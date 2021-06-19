package cool.lazy.cat.orm.core.jdbc.analyzer;

import cool.lazy.cat.orm.core.base.util.InvokeHelper;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.JdbcConfig;
import cool.lazy.cat.orm.core.jdbc.KeyWordConverter;
import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.component.id.None;
import cool.lazy.cat.orm.core.jdbc.component.validator.SimpleValidator;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.dialect.DialectRegister;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.exception.CannotResolverConditionException;
import cool.lazy.cat.orm.core.jdbc.handle.ValidateHandler;
import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import cool.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import cool.lazy.cat.orm.core.jdbc.mapping.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.provider.IdProvider;
import cool.lazy.cat.orm.core.jdbc.provider.TypeConverterProvider;
import cool.lazy.cat.orm.core.jdbc.sqlsource.PojoPropertySqlParameterSource;
import cool.lazy.cat.orm.core.jdbc.sqlsource.UpdateConditionSqlParameterSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/23 10:47
 */
public class DefaultParameterInjector implements ParameterInjector {

    @Autowired
    protected FieldInfoCatcher fieldInfoCatcher;
    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected ConditionAnalyzer conditionAnalyzer;
    @Autowired
    protected KeyWordConverter keyWordConverter;
    @Autowired
    protected TypeConverterProvider typeConverterProvider;
    @Autowired
    protected IdProvider idProvider;
    @Autowired
    protected ValidateHandler validateHandler;
    @Autowired
    protected JdbcConfig jdbcConfig;
    @Autowired
    protected DialectRegister dialectRegister;
    protected SimpleValidator simpleValidator = new SimpleValidator();

    @Override
    public Object injectOfSelect(SearchParam searchParam, StringBuilder sql) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(searchParam.getPojoType()).getTableInfo();
        SqlParamHolder analysis = conditionAnalyzer.analysis(searchParam);
        /*if (sqlParam.needPaging() && tableInfo.isNested()) {
            if (null != sqlParam.getCondition()) {
                if (Condition.EMPTY_CONDITION != sqlParam.getCondition()) {
                    this.checkPageCondition(sqlParam.getPojoType(), sqlParam.getCondition(), new ExistMainTableFilterHolder(false));
                }
            } else {
                this.checkPageCondition(sqlParam.getPojoType(), sqlParam.getParams());
            }
        }*/
        if (analysis.getSql().length() > 0) {
            sql.append(keyWordConverter.where()).append(analysis.getSql());
        }
        return analysis.getParam();
    }

    private void checkPageCondition(Class<?> pojoType, Condition condition, List<TableChain> nestedChain, ExistMainTableFilterHolder holder) {
        TableFieldInfo fieldInfo = fieldInfoCatcher.getByName(pojoType, condition.getField(), false);
        if (null != fieldInfo) {
            holder.hasMainTableFilter = true;
        }
        TableFieldInfoWrapper fieldInfoWrapper = fieldInfoCatcher.getNestedFiledByName(nestedChain, condition.getField(), false);
        if (holder.hasMainTableFilter && fieldInfoWrapper != null && fieldInfoWrapper.getTableChain() != null) {
            PojoMapping pojoMapping = fieldInfoWrapper.getTableChain().getPojoMapping();
            if (pojoMapping instanceof OneToManyMapping) {
                throw new CannotResolverConditionException("存在一对多映射，无法解析对从表的条件过滤，将导致分页失效！\t"
                        + fieldInfoWrapper.getTableChain().getPojoType().getName() + "#" + condition.getField()  + "\t" + condition.getType());
            }
        }
        if (null != condition.getAnd()) {
            for (Condition c : condition.getAnd()) {
                this.checkPageCondition(pojoType, c, nestedChain, holder);
            }
        }
        if (null != condition.getOr()) {
            for (Condition c : condition.getOr()) {
                this.checkPageCondition(pojoType, c, nestedChain, holder);
            }
        }
    }

    private void checkPageCondition(Class<?> pojoType, Map<String, Object> params, List<TableChain> nestedChain) {
        boolean hasMainTableFilter = false;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (StringUtil.isBlank(entry.getKey()) || null == entry.getValue() || StringUtil.isBlank(entry.getValue().toString())) {
                continue;
            }
            boolean isNested = entry.getKey().contains(".");
            if (isNested) {
                TableFieldInfoWrapper wrapper = fieldInfoCatcher.getNestedFiledByName(nestedChain, entry.getKey(), false);
                if (null != wrapper && wrapper.getFieldInfo().havingQueryFilter()) {
                    if (hasMainTableFilter && wrapper.getTableChain().getPojoMapping() instanceof OneToManyMapping) {
                        throw new CannotResolverConditionException("存在一对多映射，无法解析对从表的条件过滤，将导致分页失效！\t"
                                + wrapper.getTableChain().getPojoType().getName() + "#" + wrapper.getFieldInfo().getJavaFieldName()  + "\t" + wrapper.getFieldInfo().getColumn().getQueryFilterType());
                    }
                }
            } else {
                TableFieldInfo fieldInfo = fieldInfoCatcher.getByName(pojoType, entry.getKey(), false);
                if (fieldInfo.havingQueryFilter()) {
                    hasMainTableFilter = true;
                }
            }
        }
    }

    private final static class ExistMainTableFilterHolder {
        boolean hasMainTableFilter;

        public ExistMainTableFilterHolder(boolean hasMainTableFilter) {
            this.hasMainTableFilter = hasMainTableFilter;
        }
    }

    @Override
    public Object injectOfInsert(UpdateParam updateParam, StringBuilder sql) {
        Object instance = updateParam.getData();
        TableInfo tableInfo = pojoTableManager.getByPojoType(updateParam.getPojoType()).getTableInfo();
        Map<String, TableFieldInfo> fieldInfoMap = tableInfo.getFieldInfoList().stream().filter(f -> f.getColumn().isInsertable())
                .collect(Collectors.toMap(TableFieldInfo::getJavaFieldName, Function.identity()));
        List<TableFieldInfo> havingConvertFields = tableInfo.getFieldInfoList().stream().filter(TableFieldInfo::havingTypeConverter).collect(Collectors.toList());
        if (instance instanceof Collection) {
            return this.providerSqlParamSources(havingConvertFields, (Collection<?>) instance, fieldInfoMap);
        } else {
            return new PojoPropertySqlParameterSource(instance, fieldInfoMap, this.convert(havingConvertFields, instance));
        }
    }

    private PojoPropertySqlParameterSource[] providerSqlParamSources(List<TableFieldInfo> fieldInfoList, Collection<?> data, Map<String, TableFieldInfo> fieldInfoMap) {
        PojoPropertySqlParameterSource[] sources = new PojoPropertySqlParameterSource[data.size()];
        int index = 0;
        for (Object o : data) {
            sources[index ++] = new PojoPropertySqlParameterSource(o, fieldInfoMap, this.convert(fieldInfoList, o));
        }
        return sources;
    }

    @Override
    public void injectId(IdStrategy id, Object data) {
        if (id.getIdGenerator() != Auto.class && id.getIdGenerator() != None.class) {
            if (data instanceof Collection) {
                Collection<?> dataRef = (Collection<?>) data;
                Object[] ids = idProvider.provider(id.getIdGenerator(), data);
                int i = 0;
                for (Object o : dataRef) {
                    InvokeHelper.invokeSetter(id.getSetter(), o, ids[i ++]);
                }
            } else {
                InvokeHelper.invokeSetter(id.getSetter(), data, idProvider.provider(id.getIdGenerator(), data)[0]);
            }
        }
    }

    private Map<TableFieldInfo, Object> convert(List<TableFieldInfo> fieldInfoList, Object data) {
        if (fieldInfoList.isEmpty()) {
            return null;
        }
        Map<TableFieldInfo, Object> converted = new HashMap<>();
        fieldInfoList.forEach(f -> {
            TypeConverter typeConverter = typeConverterProvider.provider(f.getColumn().getTypeConverter());
            if (typeConverter.match(dialectRegister.getDialect())) {
                converted.put(f, typeConverter.convertToDb(data, InvokeHelper.invokeGetter(f.getGetter(), data), f));
            }
        });
        return converted;
    }

    @Override
    public Object injectOfDelete(UpdateParam updateParam, StringBuilder sql) {
        SqlParamHolder analysis = conditionAnalyzer.analysis(updateParam.getPojoType(), updateParam.getCondition());
        sql.append(analysis.getSql());
        return analysis.getParam();
    }

    @Override
    public Object injectOfLogicDelete(UpdateParam updateParam, StringBuilder sql) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(updateParam.getPojoType()).getTableInfo();
        Map<String, TableFieldInfo> fieldInfoMap = tableInfo.getFieldInfoList().stream().filter(f -> f.getColumn().isInsertable())
                .collect(Collectors.toMap(TableFieldInfo::getJavaFieldName, Function.identity()));
        LogicDeleteField logicDeleteField = pojoTableManager.getByPojoType(updateParam.getPojoType()).getTableInfo().getLogicDeleteField();
        SqlParamHolder analysis = conditionAnalyzer.analysis(updateParam.getPojoType(), updateParam.getCondition());
        sql.append(analysis.getSql());
        analysis.getParam().put(logicDeleteField.getJavaFieldName(), logicDeleteField.getDeleteValue());
        return new UpdateConditionSqlParameterSource(updateParam.getData(), fieldInfoMap, null, analysis.getParam());
    }

    @Override
    public Object injectOfUpdate(UpdateParam updateParam, StringBuilder sql) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(updateParam.getPojoType()).getTableInfo();
        Map<String, TableFieldInfo> fieldInfoMap = tableInfo.getFieldInfoList().stream().filter(f -> f.getColumn().isInsertable())
                .collect(Collectors.toMap(TableFieldInfo::getJavaFieldName, Function.identity()));
        List<TableFieldInfo> havingConvertFields = tableInfo.getFieldInfoList().stream().filter(TableFieldInfo::havingTypeConverter).collect(Collectors.toList());
        SqlParamHolder analysis = conditionAnalyzer.analysis(updateParam.getPojoType(), updateParam.getCondition());
        sql.append(analysis.getSql());
        return new UpdateConditionSqlParameterSource(updateParam.getData(), fieldInfoMap, this.convert(havingConvertFields, updateParam.getData()), analysis.getParam());
    }
}
