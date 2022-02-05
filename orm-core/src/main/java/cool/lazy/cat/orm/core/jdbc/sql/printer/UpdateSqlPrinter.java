package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.exception.UnKnowFiledException;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItem;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMappingImpl;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.source.PojoPropertySqlParameterSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterValueSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.PojoFieldSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.SetSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.SetSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.UpdateSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.UpdateSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.BehaviorDescriptorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DMLSqlStructureImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.jdbc.sql.type.Update;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/7/16 13:58
 */
public class UpdateSqlPrinter extends AbstractSqlPrinter implements SqlPrinter {

    protected final PojoTableManager pojoTableManager;
    protected final Class<? extends SqlType> type = Update.class;

    public UpdateSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
        super(correctorExecutor);
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public boolean support(SqlParameterMapping sqlParameterMapping) {
        return this.type.isAssignableFrom(sqlParameterMapping.getType()) && sqlParameterMapping.getParam() instanceof DataHolderParam && null != sqlParameterMapping.getDataOperationDescriptor()
                && (CollectionUtil.isNotEmpty(sqlParameterMapping.getDataOperationDescriptor().getUpdateData()) || CollectionUtil.isNotEmpty(sqlParameterMapping.getDataOperationDescriptor().getLogicDeleteData()));
    }

    protected List<Collection<DataOperationItem>> mergeData(DataOperationDescriptor dataOperationDescriptor) {
        boolean hasUpdateData = CollectionUtil.isNotEmpty(dataOperationDescriptor.getUpdateData());
        boolean hasLogicDeleteData = CollectionUtil.isNotEmpty(dataOperationDescriptor.getLogicDeleteData());
        if (hasUpdateData && hasLogicDeleteData) {
            return Arrays.asList(dataOperationDescriptor.getUpdateData(), dataOperationDescriptor.getLogicDeleteData());
        } else if (hasUpdateData) {
            return Collections.singletonList(dataOperationDescriptor.getUpdateData());
        } else {
            return Collections.singletonList(dataOperationDescriptor.getLogicDeleteData());
        }
    }

    protected List<ParameterMapping> initParameterMappings(SqlParameterMapping sqlParameterMapping) {
        List<ParameterMapping> mappings = new ArrayList<>();
        List<Collection<DataOperationItem>> mergeData = this.mergeData(sqlParameterMapping.getDataOperationDescriptor());
        for (Collection<DataOperationItem> items : mergeData) {
            for (DataOperationItem item : items) {
                TableInfo tableInfo = pojoTableManager.getByPojoType(item.getPojoType()).getTableInfo();
                for (Object o : item) {
                    ParameterMappingImpl mapping = new ParameterMappingImpl(item.getPojoType());
                    List<SqlSource> sqlSources;
                    if (o instanceof SqlSource) {
                        sqlSources = Collections.singletonList(Caster.cast(o));
                    } else {
                        sqlSources = Collections.singletonList(new PojoPropertySqlParameterSource(o, tableInfo.getFieldInfoMap()));
                    }
                    mapping.setSqlSources(sqlSources);
                    mapping.setSqlStructure(new DMLSqlStructureImpl());
                    // 设置sql执行条件 合并上级param条件
                    mapping.getSqlStructure().setCondition(item.getCondition() == null ? sqlParameterMapping.getParam().getCondition() : item.getCondition());
                    if (mapping.getSqlStructure().getCondition() != sqlParameterMapping.getParam().getCondition()) {
                        mapping.getSqlStructure().getCondition().and(sqlParameterMapping.getParam().getCondition());
                    }
                    mappings.add(mapping);
                }
            }
        }
        // 初始化容器
        if (null == sqlParameterMapping.getParameterMappings()) {
            sqlParameterMapping.setParameterMappings(new ArrayList<>(mappings.size()));
        }
        return mappings;
    }

    @Override
    public void printTo(SqlParameterMapping sqlParameterMapping) {
        DataHolderParam param = Caster.cast(sqlParameterMapping.getParam());
        List<ParameterMapping> mappings = this.initParameterMappings(sqlParameterMapping);
        boolean ignoreNullField = param instanceof UpdateParam && ((UpdateParam) param).ignoreNullField();
        Set<String> updateFields = param instanceof UpdateParam ? ((UpdateParam) param).getUpdateFields() : null;
        this.renderTableFields(mappings, sqlParameterMapping, ignoreNullField, updateFields);
        this.mergeMappings(sqlParameterMapping);
    }

    protected void renderTableFields(List<ParameterMapping> mappings, SqlParameterMapping sqlParameterMapping, boolean ignoreNullField, Set<String> updateFields) {
        String quotationMarks = JdbcOperationSupport.getAndCheck().getDialect().getDbFieldQuotationMarks();
        for (ParameterMapping mapping : mappings) {
            TableInfo tableInfo = pojoTableManager.getByPojoType(mapping.getPojoType()).getTableInfo();
            sqlParameterMapping.getParameterMappings().add(mapping);
            UpdateSqlString updateSqlString = new UpdateSqlStringImpl(new DynamicNameSqlStringImpl(tableInfo.getPojoType(), tableInfo.getSchema(), tableInfo.getName(), null, quotationMarks));
            super.renderSqlString(updateSqlString, sqlParameterMapping);
            SetSqlString set = new SetSqlStringImpl();
            updateSqlString.combination(set);
            Map<String, PojoField> fieldInfoMap = tableInfo.getFieldInfoMap();
            Map<String, PojoField> affectedFieldMapping = new HashMap<>();
            if (CollectionUtil.isNotEmpty(updateFields)) {
                this.renderTableFields(tableInfo.getPojoType(), set, fieldInfoMap, affectedFieldMapping, quotationMarks, updateFields);
            } else {
                this.renderTableFields(tableInfo.getPojoType(), set, mapping, fieldInfoMap, affectedFieldMapping, quotationMarks, ignoreNullField);
            }
            if (affectedFieldMapping.isEmpty()) {
                logger.error("sql未更新字段 被忽略更新的字段: {} 请检查@Column isUpdatable属性", affectedFieldMapping);
            }
            mapping.setAffectedFieldMapping(affectedFieldMapping);
            mapping.getSqlStructure().setBehaviorDescriptor(new BehaviorDescriptorImpl(updateSqlString, updateSqlString.getContent()));
        }
    }

    protected void renderTableFields(Class<?> pojoType, SetSqlString set, ParameterMapping mapping, Map<String, PojoField> fieldInfoMap,
                                     Map<String, PojoField> affectedFieldMapping, String quotationMarks, boolean ignoreNullField) {
        if (CollectionUtil.isEmpty(mapping.getSingleSqlSource().getParameterNames())) {
            return;
        }
        for (SqlSource sqlSource : mapping.getSqlSources()) {
            for (String parameterName : mapping.getSingleSqlSource().getParameterNames()) {
                PojoField fieldInfo = fieldInfoMap.get(parameterName);
                // 限定修改字段 > ignoreNullField > isUpdatable()
                if (ignoreNullField && null == sqlSource.getValue(parameterName)) {
                    continue;
                }
                if (null == fieldInfo) {
                    throw new UnKnowFiledException("未知的属性: " + parameterName + " #" + pojoType);
                }
                if (!fieldInfo.getColumn().isUpdatable()) {
                    continue;
                }
                affectedFieldMapping.put(fieldInfo.getJavaFieldName(), fieldInfo);
                set.combination(new PojoFieldSqlString(quotationMarks + fieldInfo.getDbFieldName() + quotationMarks, false));
                set.combination(new ParameterValueSqlStringImpl(new ParameterNameSqlStringImpl(parameterName)));
            }
        }
    }

    protected void renderTableFields(Class<?> pojoType, SetSqlString set, Map<String, PojoField> fieldInfoMap,
                                     Map<String, PojoField> affectedFieldMapping, String quotationMarks, Set<String> updateFields) {
        for (String updateField : updateFields) {
            PojoField fieldInfo = fieldInfoMap.get(updateField);
            if (null == fieldInfo) {
                throw new UnKnowFiledException("未知的属性: " + updateField + " #" + pojoType);
            }
            affectedFieldMapping.put(fieldInfo.getJavaFieldName(), fieldInfo);
            set.combination(new PojoFieldSqlString(quotationMarks + fieldInfo.getDbFieldName() + quotationMarks, false));
            set.combination(new ParameterValueSqlStringImpl(new ParameterNameSqlStringImpl(updateField)));
        }
    }

    /**
     * 尝试合并多个待执行的sql的参数到一个sql中 以提高效率
     * 合并条件在于 sql结构完全相等
     * @param sqlParameterMapping 参数映射
     */
    protected void mergeMappings(SqlParameterMapping sqlParameterMapping) {
        if (sqlParameterMapping.getParameterMappings().size() > 1) {
            List<ParameterMapping> parameterMappings = sqlParameterMapping.getParameterMappings();
            // 根据受影响的字段进行分组 如果sql修改的字段是完全一致的 并且修改条件也是完全一致的 则认为这些sql的结构是完全一致的
            Map<SqlStructureGroupKey, List<ParameterMapping>> groupByAffectedField = parameterMappings.stream().collect(Collectors.groupingBy(m -> new SqlStructureGroupKey(m.getAffectedFieldMapping(), m.getSqlStructure().getCondition())));
            sqlParameterMapping.setParameterMappings(new ArrayList<>(groupByAffectedField.size()));
            for (List<ParameterMapping> mappings : groupByAffectedField.values()) {
                ParameterMapping mappingCopy;
                if (CollectionUtil.isNotEmpty(mappings)) {
                    if (mappings.size() == 1) {
                        mappingCopy = mappings.get(0);
                    } else {
                        ParameterMapping target = mappings.get(0);
                        mappingCopy = new ParameterMappingImpl(target.getPojoType());
                        mappingCopy.setSqlStructure(target.getSqlStructure());
                        mappingCopy.setAffectedFieldMapping(target.getAffectedFieldMapping());
                        // 收集同sql结构下的资源 聚合到一个sql中
                        mappingCopy.setSqlSources(mappings.stream().flatMap(m -> m.getSqlSources().stream()).collect(Collectors.toList()));
                    }
                    sqlParameterMapping.getParameterMappings().add(mappingCopy);
                }
            }
        }
    }

    protected static final class SqlStructureGroupKey {
        private final Map<String, PojoField> affectedFieldMapping;
        private final SqlCondition sqlCondition;

        public SqlStructureGroupKey(Map<String, PojoField> affectedFieldMapping, SqlCondition sqlCondition) {
            this.affectedFieldMapping = affectedFieldMapping;
            this.sqlCondition = sqlCondition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SqlStructureGroupKey groupKey = (SqlStructureGroupKey) o;
            return Objects.equals(affectedFieldMapping, groupKey.affectedFieldMapping) && Objects.equals(sqlCondition, groupKey.sqlCondition);
        }

        @Override
        public int hashCode() {
            int result = affectedFieldMapping != null ? affectedFieldMapping.hashCode() : 0;
            result = 31 * result + (sqlCondition != null ? sqlCondition.hashCode() : 0);
            return result;
        }
    }
}
