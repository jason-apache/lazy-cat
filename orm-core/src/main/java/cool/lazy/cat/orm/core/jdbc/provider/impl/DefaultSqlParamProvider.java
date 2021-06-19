package cool.lazy.cat.orm.core.jdbc.provider.impl;

import cool.lazy.cat.orm.core.base.constant.Constant;
import cool.lazy.cat.orm.core.base.exception.UnsupportedTypeException;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.base.util.InvokeHelper;
import cool.lazy.cat.orm.core.jdbc.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.KeyWordConverter;
import cool.lazy.cat.orm.core.jdbc.analyzer.ConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.FieldInfoCatcher;
import cool.lazy.cat.orm.core.jdbc.analyzer.ParameterInjector;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.component.validator.SimpleValidator;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.dialect.DialectRegister;
import cool.lazy.cat.orm.core.jdbc.dto.CountHolder;
import cool.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.exception.EmptyColumnException;
import cool.lazy.cat.orm.core.jdbc.exception.FieldDoesNotExistException;
import cool.lazy.cat.orm.core.jdbc.handle.ValidateHandler;
import cool.lazy.cat.orm.core.jdbc.holder.SearchSqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.SearchSqlParamIndexHolder;
import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.UpdateSqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import cool.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SimpleSearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SimpleUpdateParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.provider.SqlParamProvider;
import cool.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/10 14:57
 */
public class DefaultSqlParamProvider implements SqlParamProvider {

    @Autowired
    protected KeyWordConverter keyWordConverter;
    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected FieldInfoCatcher fieldInfoCatcher;
    @Autowired
    protected ConditionAnalyzer conditionAnalyzer;
    @Autowired
    protected ParameterInjector parameterInjector;
    @Autowired
    protected ValidateHandler validateHandler;
    @Autowired
    protected TriggerProvider triggerProvider;
    protected Dialect dialect;
    protected final Log logger = LogFactory.getLog(getClass());
    protected final Validator simpleValidator = new SimpleValidator();

    @Autowired
    private void initDialect(DialectRegister dialectRegister) {
        // 初始化数据库方言
        this.dialect = dialectRegister.getDialect();
    }

    @Override
    public SearchSqlParamIndexHolder getSelectSql(SearchParam searchParam) {
        TableInfo tableInfo = searchParam.getTableInfo();
        ExcludeFieldInfoWrapper exclude = fieldInfoCatcher.filterExclude(searchParam.getPojoType(), searchParam.getNestedChain(), searchParam.getIgnorer());
        StringBuilder sql = new StringBuilder();
        sql.append(keyWordConverter.select());
        CountHolder countHolder = new CountHolder(0);
        List<TableFieldInfoIndexWrapper> indexes = new ArrayList<>(Constant.DEFAULT_CONTAINER_SIZE);
        // 主表字段
        this.renderTableField(sql, JdbcConstant.MAIN_TABLE_NAME, tableInfo.getFieldInfoList(), null, exclude, countHolder, indexes);
        if (tableInfo.isNested()) {
            // 从表字段
            this.generateChainField(sql, searchParam.getNestedChain(), exclude, countHolder, indexes);
        }
        // 剪尾
        this.tailCutting(sql);
        sql.append(keyWordConverter.from()).append(tableInfo.getFullName()).append(" ").append(JdbcConstant.MAIN_TABLE_NAME).append(" ");
        if (tableInfo.isNested()) {
            // 连接条件
            this.generateChainJoin(sql, searchParam.getNestedChain(), JdbcConstant.MAIN_TABLE_NAME, exclude);
        }
        // 参数注入
        Object param = parameterInjector.injectOfSelect(searchParam, sql);
        // 排序
        if (null != searchParam.getOrderBy() && CollectionUtil.isNotEmpty(searchParam.getOrderBy().getFields())) {
            this.oderBy(sql, searchParam);
        }
        // 分页
        if (searchParam.needPaging()) {
            if (null != dialect) {
                sql = new StringBuilder(dialect.limitSql(searchParam, sql, indexes));
            } else {
                logger.warn("未配置数据库方言，分页失效");
            }
        }
        SearchSqlParamHolder searchSqlParamHolder = new SearchSqlParamHolder(sql);
        this.adapterParam(searchSqlParamHolder, param);
        return new SearchSqlParamIndexHolder(searchSqlParamHolder, indexes, exclude);
    }

    protected void oderBy(StringBuilder sql, SearchParam searchParam) {
        sql.append(keyWordConverter.order()).append(keyWordConverter.by());
        String[] fields = searchParam.getOrderBy().getFields();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sql.append(",");
            }
            String fieldName = fields[i];
            if (fieldName.contains(".")) {
                TableFieldInfoWrapper wrapper = fieldInfoCatcher.getNestedFiledByName(searchParam.getNestedChain(), fieldName, true);
                sql.append(wrapper.getTableChain().getAliasName()).append(".").append(wrapper.getFieldInfo().getDbFieldName());
            } else {
                TableFieldInfo fieldInfo = fieldInfoCatcher.getByName(searchParam.getPojoType(), fieldName, true);
                sql.append(JdbcConstant.MAIN_TABLE_NAME).append(".").append(fieldInfo.getDbFieldName());
            }
        }
        sql.append(" ").append(searchParam.getOrderBy().isAsc() ? keyWordConverter.asc() : keyWordConverter.desc());
    }

    protected void generateChainField(StringBuilder sql, List<TableChain> nestedChainList, ExcludeFieldInfoWrapper exclude,
                                      CountHolder countHolder, List<TableFieldInfoIndexWrapper> infoIndexes) {
        for (TableChain chain : nestedChainList) {
            if (exclude.isExclude(chain)) {
                continue;
            }
            TableInfo joinTable = pojoTableManager.getByPojoType(chain.getPojoType()).getTableInfo();
            String joinTableName = chain.getAliasName();
            this.renderTableField(sql, joinTableName, joinTable.getFieldInfoList(), chain, exclude, countHolder, infoIndexes);
            if (chain.hasChain()) {
                this.generateChainField(sql, chain.getChain(), exclude, countHolder, infoIndexes);
            }
        }
    }

    protected void generateChainJoin(StringBuilder sql, List<TableChain> chainList, String preChainName, ExcludeFieldInfoWrapper exclude) {
        for (TableChain chain : chainList) {
            if (exclude.isExclude(chain)) {
                continue;
            }
            TableInfo joinTable = pojoTableManager.getByPojoType(chain.getPojoType()).getTableInfo();
            String joinTableFullName = joinTable.getFullName();
            String joinTableName = chain.getAliasName();
            sql.append(keyWordConverter.left()).append(keyWordConverter.join()).append(" ").append(joinTableFullName)
                    .append(" ").append(joinTableName).append(" ").append(keyWordConverter.on()).append(" ");
            this.renderJoinCondition(sql, chain.getJoinCondition(), joinTableName, preChainName);
            if (chain.hasChain()) {
                this.generateChainJoin(sql, chain.getChain(), joinTableName, exclude);
            }
        }
    }

    protected void renderJoinCondition(StringBuilder sql, List<On> joinCondition, String joinTable, String originalTable) {
        for (int i = 0; i < joinCondition.size(); i++) {
            On on = joinCondition.get(i);
            if (i > 0) {
                sql.append(keyWordConverter.and());
            }
            sql.append(joinTable).append(".").append(on.getTargetFiledInfo().getDbFieldName()).append(" = ")
                    .append(originalTable).append(".").append(on.getForeignKeyInfo().getDbFieldName()).append(" ");
        }
    }

    protected void renderTableField(StringBuilder sql, String tableAliasName, List<TableFieldInfo> fieldInfoList, TableChain chain,
                                    ExcludeFieldInfoWrapper exclude, CountHolder countHolder, List<TableFieldInfoIndexWrapper> infoIndexes) {
        for (TableFieldInfo info : fieldInfoList) {
            if (exclude.isExclude(info)) {
                continue;
            }
            if (null != chain && exclude.isExclude(chain, info)) {
                continue;
            }
            if (null != tableAliasName) {
                sql.append(tableAliasName).append(".").append(info.getDbFieldName()).append(" ").append(keyWordConverter.as())
                        .append("\"").append(tableAliasName).append(".").append(info.getAliasName()).append("\"").append(", ");
                countHolder.count ++;
                infoIndexes.add(new TableFieldInfoIndexWrapper().setIndex(countHolder.count).setFieldInfo(info)
                        .setChainFlatIndex(chain == null ? -1 : chain.getFlatIndex()));
            } else {
                sql.append(info.getDbFieldName()).append(" ").append(", ");
            }
        }
    }

    private void tailCutting(StringBuilder sql) {
        sql.deleteCharAt(sql.length() -1);
        sql.deleteCharAt(sql.length() -1);
        sql.append(" ");
    }

    @Override
    public SearchSqlParamIndexHolder getCountSql(SearchParam searchParam) {
        SimpleSearchParam copySqlParam = new SimpleSearchParam(searchParam.getTableInfo());
        BeanUtils.copyProperties(searchParam, copySqlParam);
        SearchSqlParamIndexHolder paramIndexHolder = this.getSelectSql(copySqlParam.setPageSize(-1).setOrderBy(null));
        StringBuilder sql = new StringBuilder(paramIndexHolder.getSql());
        if (null != dialect) {
            sql = new StringBuilder(dialect.countSql(copySqlParam, sql));
        } else {
            sql = new StringBuilder().append(keyWordConverter.select()).append(keyWordConverter.count()).append("(0)").append(keyWordConverter.from())
                    .append("(").append(sql).append(") ").append(JdbcConstant.COUNT_TABLE_NAME);
        }
        return new SearchSqlParamIndexHolder(new SearchSqlParamHolder(sql, paramIndexHolder.getParam()), null, null);
    }

    @Override
    public SqlParamHolder getInsertSql(UpdateParam updateParam) {
        this.checkData(updateParam.getData());
        Class<?> pojoType = this.getTypeFromData(updateParam.getData());
        StringBuilder sql = new StringBuilder();
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        IdStrategy id = tableInfo.getId();
        Map<String, TableFieldInfo> fieldInfoMap = tableInfo.getFieldInfoList().stream().filter(f -> f.getColumn().isInsertable())
                .collect(Collectors.toMap(TableFieldInfo::getJavaFieldName, Function.identity()));
        if (id.getIdGenerator() == Auto.class) {
            fieldInfoMap.remove(id.getJavaFieldName());
        }
        sql.append(keyWordConverter.insert()).append(keyWordConverter.into()).append(tableInfo.getFullName()).append(" ");
        sql.append("(");
        fieldInfoMap.values().forEach(f -> sql.append(f.getDbFieldName()).append(", "));
        this.tailCutting(sql);
        sql.append(") ");
        sql.append(keyWordConverter.values());
        sql.append("(");
        fieldInfoMap.values().forEach(f -> sql.append(":").append(f.getJavaFieldName()).append(", "));
        this.tailCutting(sql);
        sql.append(") ");
        this.validate(tableInfo.getFieldInfoList(), updateParam.getData(), true);
        // 先进行id的赋值，再执行触发器
        parameterInjector.injectId(tableInfo.getId(), updateParam.getData());
        this.trigger(tableInfo, updateParam.getData(), true);
        Object param = parameterInjector.injectOfInsert(new SimpleUpdateParam(updateParam).setPojoType(pojoType), null);
        UpdateSqlParamHolder holder = new UpdateSqlParamHolder(sql);
        this.adapterParam(holder, param);
        return holder;
    }

    private void checkData(Object data) {
        if (null == data) {
            throw new NullPointerException("参数不能为空");
        }
        if (data.getClass().isArray()) {
            throw new UnsupportedTypeException("不支持数组，期望的类型："  + Collection.class.getName());
        }
    }

    private Class<?> getTypeFromData(Object obj) {
        if (obj instanceof Collection) {
            for (Object o : ((Collection<?>) obj)) {
                return o.getClass();
            }
        }
        return obj.getClass();
    }

    private void trigger(TableInfo tableInfo, Object data, boolean onInsert) {
        if (tableInfo.havingTrigger()) {
            tableInfo.getTriggerInfoList().forEach(t -> triggerProvider.provider(t.getTrigger()).execute(data, tableInfo, onInsert));
        }
    }

    private void validate(List<TableFieldInfo> fieldInfoList, Object data, boolean onInsert) {
        List<TableFieldInfo> simpleValidate;
        if (onInsert) {
            fieldInfoList = fieldInfoList.stream().filter(f -> f.havingValidator() && f.insertable()).collect(Collectors.toList());
            simpleValidate = fieldInfoList.stream().filter(f -> f.havingSimpleValidator() && f.insertable()).collect(Collectors.toList());
        } else {
            fieldInfoList = fieldInfoList.stream().filter(f -> f.havingValidator() && f.updatable()).collect(Collectors.toList());
            simpleValidate = fieldInfoList.stream().filter(f -> f.havingSimpleValidator() && f.updatable()).collect(Collectors.toList());
        }
        fieldInfoList.forEach(f -> validateHandler.handle(f, data));
        this.simpledValidate(simpleValidate, data);
    }

    private void simpledValidate(List<TableFieldInfo> simpleValidate, Object data) {
        if (!simpleValidate.isEmpty()) {
            if (data instanceof Collection) {
                Collection<?> dataRef = (Collection<?>) data;
                for (Object o : dataRef) {
                    for (TableFieldInfo s : simpleValidate) {
                        simpleValidator.validate(s.getColumn(), InvokeHelper.invokeGetter(s.getGetter(), o));
                    }
                }
            } else {
                for (TableFieldInfo s : simpleValidate) {
                    simpleValidator.validate(s.getColumn(), InvokeHelper.invokeGetter(s.getGetter(), data));
                }
            }
        }
    }

    @Override
    public SqlParamHolder getUpdateSql(UpdateParam updateParam) {
        Object data = updateParam.getData();
        this.checkData(data);
        String[] ignoreFields = updateParam.getIgnoreFields();
        Class<?> pojoType = this.getTypeFromData(data);
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        List<TableFieldInfo> fieldInfoList = tableInfo.getFieldInfoList().stream().filter(f -> f.getColumn().isUpdatable()).collect(Collectors.toList());
        List<String> ignoreFieldList = new ArrayList<>(fieldInfoList.size());
        if (updateParam.getIgnoreNull()) {
            this.filterNull(data, fieldInfoList, ignoreFieldList);
        }
        if (null != ignoreFields && ignoreFields.length > 0) {
            if (!fieldInfoCatcher.pojoFieldOnly(pojoType, ignoreFields)) {
                throw new UnsupportedOperationException("修改操作只允许操作本对象中的属性 #" + pojoType.getName());
            }
            this.filterIgnore(ignoreFieldList, ignoreFields);
        }
        if (ignoreFieldList.size() >= fieldInfoList.size()) {
            throw new EmptyColumnException("所有字段为空或已被忽略！" + pojoType.getName());
        }
        ExcludeFieldInfoWrapper excludeFieldInfoWrapper = fieldInfoCatcher.filterExclude(pojoType, tableInfo.getNestedChain(), Ignorer.build(ignoreFieldList.toArray(new String[0])));
        List<TableFieldInfo> effectiveFields = tableInfo.getFieldInfoList().stream().filter(f -> !excludeFieldInfoWrapper.isExclude(f.getJavaFieldName())).collect(Collectors.toList());
        this.validate(effectiveFields, data, false);
        this.trigger(tableInfo, data, false);
        StringBuilder sql = new StringBuilder();
        sql.append(keyWordConverter.update()).append(tableInfo.getFullName()).append(" ").append(keyWordConverter.set());
        for (TableFieldInfo fieldInfo : effectiveFields) {
            sql.append(fieldInfo.getDbFieldName()).append(" =:").append(fieldInfo.getJavaFieldName()).append(",");
        }
        sql.deleteCharAt(sql.length() -1).append(" ");
        UpdateSqlParamHolder holder = new UpdateSqlParamHolder(sql);
        Object param = parameterInjector.injectOfUpdate(new SimpleUpdateParam(updateParam).setPojoType(pojoType), sql);
        this.adapterParam(holder, param);
        return holder;
    }

    private void filterNull(Object instance, List<TableFieldInfo> fieldInfoList, List<String> columnNameList) {
        fieldInfoList.stream().filter(f -> InvokeHelper.invokeGetter(f.getGetter(), instance) == null).forEach(f -> columnNameList.add(f.getJavaFieldName()));
    }

    private void filterIgnore(List<String> columnNameList, String[] ignoreFields) {
        columnNameList.addAll(Arrays.asList(ignoreFields));
    }

    @Override
    public SqlParamHolder getDeleteSql(UpdateParam updateParam) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(updateParam.getPojoType()).getTableInfo();
        StringBuilder sql = new StringBuilder();
        sql.append(keyWordConverter.delete()).append(keyWordConverter.from()).append(tableInfo.getFullName()).append(" ");
        Object param = parameterInjector.injectOfDelete(updateParam, sql);
        UpdateSqlParamHolder holder = new UpdateSqlParamHolder(sql);
        this.adapterParam(holder, param);
        return holder;
    }

    @Override
    public SqlParamHolder getLogicDeleteSql(UpdateParam updateParam) {
        Class<?> pojoType = this.getTypeFromData(updateParam.getData());
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        if (null == tableInfo.getLogicDeleteField()) {
            throw new FieldDoesNotExistException("pojo不存在逻辑删除字段：#" + tableInfo.getPojoType().getName());
        }
        StringBuilder sql = new StringBuilder();
        this.trigger(tableInfo, updateParam.getData(), false);
        sql.append(keyWordConverter.update()).append(tableInfo.getFullName()).append(" ").append(keyWordConverter.set())
                .append(tableInfo.getLogicDeleteField().getDbFieldName()).append(" =:").append(tableInfo.getLogicDeleteField().getJavaFieldName()).append(" ");
        Object param = parameterInjector.injectOfLogicDelete(new SimpleUpdateParam(updateParam).setPojoType(pojoType), sql);
        UpdateSqlParamHolder holder = new UpdateSqlParamHolder(sql);
        this.adapterParam(holder, param);
        return holder;
    }

    private void adapterParam(SqlParamHolder holder, Object param) {
        if (null == param) {
            return;
        }
        if (param instanceof Map) {
            holder.setParam((Map) param);
        } else if (param instanceof SqlParameterSource) {
            holder.setParamSource((SqlParameterSource) param);
        } else if (param.getClass().isArray() && ((Object[]) param).length > 0 && ((Object[]) param)[0] instanceof SqlParameterSource) {
            holder.setParamSources((SqlParameterSource[]) param);
        }
    }
}
