package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItem;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMappingImpl;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.source.MapSqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.PojoPropertySqlParameterSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.DeleteFromSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.DeleteFromSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.BehaviorDescriptorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DMLSqlStructureImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.Delete;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/15 15:23
 */
public class DeleteSqlPrinter extends AbstractSqlPrinter {

    protected final PojoTableManager pojoTableManager;
    protected final Class<? extends SqlType> type = Delete.class;

    public DeleteSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
        super(correctorExecutor);
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public boolean support(SqlParameterMapping sqlParameterMapping) {
        return this.type.isAssignableFrom(sqlParameterMapping.getType()) && sqlParameterMapping.getParam() instanceof DataHolderParam  && null != sqlParameterMapping.getDataOperationDescriptor()
                && CollectionUtil.isNotEmpty(sqlParameterMapping.getDataOperationDescriptor().getDeleteData());
    }

    protected List<ParameterMapping> initParameterMappings(SqlParameterMapping sqlParameterMapping) {
        DataHolderParam param = Caster.cast(sqlParameterMapping.getParam());
        if (CollectionUtil.isNotEmpty(sqlParameterMapping.getDataOperationDescriptor().getDeleteData())) {
            List<ParameterMapping> mappings = new ArrayList<>(sqlParameterMapping.getDataOperationDescriptor().getDeleteData().size());
            for (DataOperationItem item : sqlParameterMapping.getDataOperationDescriptor().getDeleteData()) {
                TableInfo tableInfo = pojoTableManager.getByPojoType(item.getPojoType()).getTableInfo();
                List<SqlSource> sqlSources;
                ParameterMappingImpl mapping = new ParameterMappingImpl(item.getPojoType());
                if (CollectionUtil.isNotEmpty(item.values())) {
                    sqlSources = new ArrayList<>(item.values().size());
                    for (Object o : item) {
                        if (o instanceof SqlSource) {
                            sqlSources.add(Caster.cast(o));
                        } else {
                            sqlSources.add(new PojoPropertySqlParameterSource(o, tableInfo.getFieldInfoMap()));
                        }
                    }
                } else {
                    sqlSources = Collections.singletonList(new MapSqlSource(item.getPojoType()));
                }
                mapping.setSqlSources(sqlSources);
                mapping.setSqlStructure(new DMLSqlStructureImpl());
                // 设置sql执行条件 合并上级param条件
                mapping.getSqlStructure().setCondition(item.getCondition() == null ? sqlParameterMapping.getParam().getCondition() : item.getCondition());
                if (mapping.getSqlStructure().getCondition() != sqlParameterMapping.getParam().getCondition()) {
                    mapping.getSqlStructure().getCondition().and(param.getCondition());
                }
                mappings.add(mapping);
            }
            // 初始化容器
            if (null == sqlParameterMapping.getParameterMappings()) {
                sqlParameterMapping.setParameterMappings(new ArrayList<>(mappings.size()));
            }
            return mappings;
        } else {
            ParameterMappingImpl mapping = new ParameterMappingImpl(param.getPojoType());
            mapping.setSqlSources(Collections.singletonList(new MapSqlSource(param.getPojoType())));
            mapping.setSqlStructure(new DMLSqlStructureImpl());
            // 取上级param中全局条件
            mapping.getSqlStructure().setCondition(sqlParameterMapping.getParam().getCondition());
            // 初始化容器
            if (null == sqlParameterMapping.getParameterMappings()) {
                sqlParameterMapping.setParameterMappings(new ArrayList<>(1));
            }
            return Collections.singletonList(mapping);
        }
    }

    @Override
    public void printTo(SqlParameterMapping sqlParameterMapping) {
        List<ParameterMapping> mappings = this.initParameterMappings(sqlParameterMapping);
        String quotationMarks = JdbcOperationSupport.getDialect().getDbFieldQuotationMarks();
        for (ParameterMapping mapping : mappings) {
            sqlParameterMapping.getParameterMappings().add(mapping);
            TableInfo tableInfo = pojoTableManager.getByPojoType(mapping.getPojoType()).getTableInfo();
            DeleteFromSqlString deleteFromSqlString = new DeleteFromSqlStringImpl(new DynamicNameSqlStringImpl(tableInfo.getPojoType(), tableInfo.getSchema(), tableInfo.getName(), null, quotationMarks));
            sqlParameterMapping.getCurrentlyProcessed().getSqlStructure().setBehaviorDescriptor(new BehaviorDescriptorImpl(deleteFromSqlString, deleteFromSqlString.getContent()));
            super.renderSqlString(deleteFromSqlString, sqlParameterMapping);
        }
    }
}
