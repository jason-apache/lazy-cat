package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.base.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItem;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMappingImpl;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.source.PojoPropertySqlParameterSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.PojoFieldSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.InsertIntoSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.InsertIntoSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.ValuesSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.ValuesSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.BehaviorDescriptorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DMLSqlStructureImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/16 13:58
 */
public class InsertSqlPrinter extends AbstractSqlPrinter implements SqlPrinter {

    protected final PojoTableManager pojoTableManager;
    private final Class<? extends SqlType> type = Insert.class;

    public InsertSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
        super(correctorExecutor);
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public boolean support(SqlParameterMapping sqlParameterMapping) {
        return this.type.isAssignableFrom(sqlParameterMapping.getType()) && sqlParameterMapping.getParam() instanceof DataHolderParam && null != sqlParameterMapping.getDataOperationDescriptor()
                && CollectionUtil.isNotEmpty(sqlParameterMapping.getDataOperationDescriptor().getInsertData());
    }

    protected List<ParameterMapping> initParameterMappings(SqlParameterMapping sqlParameterMapping) {
        List<ParameterMapping> mappings = new ArrayList<>(sqlParameterMapping.getDataOperationDescriptor().getInsertData().size());
        for (DataOperationItem item : sqlParameterMapping.getDataOperationDescriptor().getInsertData()) {
            List<SqlSource> sqlSources = new ArrayList<>(item.values().size());
            Map<String, PojoField> fieldInfoMap = pojoTableManager.getByPojoType(item.getPojoType()).getTableInfo().getFieldInfoMap();
            ParameterMappingImpl mapping = new ParameterMappingImpl(item.getPojoType());
            for (Object o : item) {
                sqlSources.add(new PojoPropertySqlParameterSource(o, fieldInfoMap));
            }
            mapping.setSqlSources(sqlSources);
            mapping.setSqlStructure(new DMLSqlStructureImpl());
            mappings.add(mapping);
        }
        // 初始化容器
        if (null == sqlParameterMapping.getParameterMappings()) {
            sqlParameterMapping.setParameterMappings(new ArrayList<>(mappings.size()));
        }
        return mappings;
    }

    @Override
    public void printTo(SqlParameterMapping sqlParameterMapping) {
        List<ParameterMapping> mappings = this.initParameterMappings(sqlParameterMapping);
        this.renderPojoFields(sqlParameterMapping, mappings);
    }

    protected void renderPojoFields(SqlParameterMapping sqlParameterMapping, List<ParameterMapping> mappings) {
        // 获得方言中对表字段转义的引号
        String q = JdbcOperationSupport.getAndCheck().getDialect().getDbFieldQuotationMarks();
        for (ParameterMapping mapping : mappings) {
            // 添加mapping
            sqlParameterMapping.getParameterMappings().add(mapping);
            SqlSource singleSqlSource = mapping.getSingleSqlSource();
            if (CollectionUtil.isEmpty(singleSqlSource.getParameterNames())) {
                continue;
            }
            TableInfo tableInfo = pojoTableManager.getByPojoType(mapping.getPojoType()).getTableInfo();
            Map<String, PojoField> affectedFieldMapping = new HashMap<>(tableInfo.getFieldInfoMap().size());
            InsertIntoSqlString insertInto = new InsertIntoSqlStringImpl(new DynamicNameSqlStringImpl(tableInfo.getPojoType(), tableInfo.getSchema(), tableInfo.getName(), null, q));
            ValuesSqlString valuesSqlString = new ValuesSqlStringImpl();
            for (String fieldName : singleSqlSource.getParameterNames()) {
                PojoField pojoField = tableInfo.getFieldInfoMap().get(fieldName);
                if (pojoField instanceof IdField && tableInfo.getId().getIdGenerator() == Auto.class) {
                    continue;
                }
                if (pojoField.getColumn().isInsertable()) {
                    insertInto.combination(new PojoFieldSqlString(q + pojoField.getDbFieldName() + q, false));
                    valuesSqlString.combination(new ParameterNameSqlStringImpl(pojoField.getJavaFieldName()));
                    affectedFieldMapping.put(pojoField.getJavaFieldName(), pojoField);
                }
            }
            mapping.setAffectedFieldMapping(affectedFieldMapping);
            mapping.getSqlStructure().setBehaviorDescriptor(new BehaviorDescriptorImpl(insertInto, insertInto.getContent()));
            super.renderSqlString(insertInto, sqlParameterMapping);
            super.renderSqlString(valuesSqlString, sqlParameterMapping);
        }
    }
}
