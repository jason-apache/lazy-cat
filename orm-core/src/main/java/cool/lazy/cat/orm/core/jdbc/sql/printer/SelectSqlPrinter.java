package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.base.constant.JoinMode;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.CascadeSelfAdaptionFieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldDescriptor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMappingImpl;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.source.EmptySqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.MapSqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.PojoFieldSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.JoinConditionSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.FromSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.FromSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.InnerJoinSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.JoinSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.LeftJoinSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.OrderBySqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.OrderBySqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.RightJoinSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.SelectSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.SelectSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.BehaviorDescriptorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructureImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/16 13:58
 * 查询sql语句打印机
 */
public class SelectSqlPrinter extends AbstractSqlPrinter implements SqlPrinter {

    protected final PojoTableManager pojoTableManager;
    private final Class<? extends SqlType> type = Select.class;

    public SelectSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
        super(correctorExecutor);
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public boolean support(SqlParameterMapping sqlParameterMapping) {
        return this.type.isAssignableFrom(sqlParameterMapping.getType()) && sqlParameterMapping.getParam() instanceof SearchParam;
    }

    /**
     * 初始化sql参数映射
     */
    protected void initParameterMappings(SqlParameterMapping sqlParameterMapping) {
        Param param = sqlParameterMapping.getParam();
        ParameterMappingImpl parameterMapping = new ParameterMappingImpl(param.getPojoType());
        // map结构的sql参数载体
        parameterMapping.setSqlSources(null == param.getCondition() ? Collections.singletonList(new EmptySqlSource(param.getPojoType())) : Collections.singletonList(new MapSqlSource(param.getPojoType())));
        parameterMapping.setSqlStructure(new DQLSqlStructureImpl());
        if (null == sqlParameterMapping.getParameterMappings()) {
            sqlParameterMapping.setParameterMappings(Collections.singletonList(parameterMapping));
        } else {
            sqlParameterMapping.getParameterMappings().add(parameterMapping);
        }
    }

    /**
     * 初始化字段访问器
     */
    protected void initFieldAccessor(SearchParam<?> searchParam) {
        FieldAccessor fieldAccessor = searchParam.getFieldAccessor();
        if (null == fieldAccessor) {
            if (searchParam.getIgnorer() != null) {
                fieldAccessor = new CascadeSelfAdaptionFieldAccessor(pojoTableManager.getByPojoType(searchParam.getPojoType()).getTableInfo());
                fieldAccessor.setIgnore(searchParam.getIgnorer());
            } else {
                fieldAccessor = pojoTableManager.getByPojoType(searchParam.getPojoType()).getTableInfo().getFieldMapper().getFieldAccessor();
            }
        } else {
            if (searchParam.getIgnorer() != null) {
                fieldAccessor.setIgnore(searchParam.getIgnorer());
            }
        }
        fieldAccessor.init();
        searchParam.setFieldAccessor(fieldAccessor);
    }

    @Override
    public void printTo(SqlParameterMapping sqlParameterMapping) {
        SearchParam<?> searchParam = Caster.cast(sqlParameterMapping.getParam());
        // 初始化参数映射
        this.initParameterMappings(sqlParameterMapping);
        this.initFieldAccessor(searchParam);
        FieldAccessor fieldAccessor = searchParam.getFieldAccessor();
        // 构建查询字段
        SelectSqlString select = this.buildSelectSqlString(sqlParameterMapping, fieldAccessor);
        // 表连接
        FromSqlString from = this.buildFromSqlString(sqlParameterMapping, fieldAccessor.getRootTableNode(), fieldAccessor);
        OrderBySqlString orderBySqlString = this.buildOrderBy(searchParam.getOrderBy(), fieldAccessor);
        if (null != orderBySqlString) {
            super.renderSqlString(orderBySqlString, sqlParameterMapping);
        }
        DQLSqlStructure dqlSqlStructure = ((DQLSqlStructure) sqlParameterMapping.getCurrentlyProcessed().getSqlStructure());
        dqlSqlStructure.setBehaviorDescriptor(new BehaviorDescriptorImpl(select, select.getContent()));
        dqlSqlStructure.setFromSqlString(from);
        dqlSqlStructure.setOrderBy(orderBySqlString);
    }

    protected SelectSqlString buildSelectSqlString(SqlParameterMapping sqlParameterMapping, FieldAccessor fieldAccessor) {
        SelectSqlString select = new SelectSqlStringImpl();
        this.renderTableField(select, sqlParameterMapping, fieldAccessor);
        super.renderSqlString(select, sqlParameterMapping);
        return select;
    }

    protected FromSqlString buildFromSqlString(SqlParameterMapping sqlParameterMapping, TableNode rootTableNode, FieldAccessor fieldAccessor) {
        FromSqlString from = new FromSqlStringImpl(new DynamicNameSqlStringImpl(rootTableNode.getPojoType(), rootTableNode.getSchema(), rootTableNode.tableName(), rootTableNode.tableAliasName(), JdbcOperationSupport.getDialect().getDbFieldQuotationMarks()));
        // 表连接
        this.renderTableJoin(from, fieldAccessor);
        super.renderSqlString(from, sqlParameterMapping);
        return from;
    }

    /**
     * 添加查询字段sql字符
     */
    protected void renderTableField(SelectSqlString select, SqlParameterMapping sqlParameterMapping, FieldAccessor fieldAccessor) {
        List<FieldDescriptor> fieldDescriptors = fieldAccessor.getFieldDescriptors();
        ParameterMapping parameterMapping = sqlParameterMapping.getCurrentlyProcessed();
        Map<String, PojoField> affectedFieldMapping = new HashMap<>(fieldDescriptors.size());
        Dialect dialect = JdbcOperationSupport.getDialect();
        String q = dialect.getDbFieldQuotationMarks();
        KeywordDictionary keywordDictionary = JdbcOperationSupport.getDialect().getKeywordDictionary();
        for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
            String fieldName = fieldDescriptor.getTableNode().tableAliasName() + "." + q + fieldDescriptor.getDbFieldName() + q;
            String aliasName = keywordDictionary.as() + " " + q + fieldDescriptor.getTableNode().tableAliasName() + "." + fieldDescriptor.getAliasName() + q;
            // 如果字段名是由@Column指定的 则这个字段名将不受大小写影响
            select.combination(new PojoFieldSqlString(fieldName + " " + aliasName, fieldDescriptor.specified()));
            affectedFieldMapping.put(fieldDescriptor.getJavaFieldName(), fieldDescriptor);
        }
        parameterMapping.setAffectedFieldMapping(affectedFieldMapping);
    }

    /**
     * 处理关联表
     */
    protected void renderTableJoin(FromSqlString from, FieldAccessor fieldAccessor) {
        if (null != fieldAccessor.getTableNodeMapping() && !fieldAccessor.getTableNodeMapping().isEmpty()) {
            Dialect dialect = JdbcOperationSupport.getDialect();
            for (TableNode tableNode : fieldAccessor.getTableNodeMapping().values()) {
                if (null == tableNode.getBelongPojoTable()) {
                    continue;
                }
                JoinSqlString joinSqlString = this.buildJoinSqlString(tableNode.getPojoMapping().getJoinMode(), new DynamicNameSqlStringImpl(tableNode.getPojoType(), tableNode.getSchema(), tableNode.tableName(), tableNode.tableAliasName(), dialect.getDbFieldQuotationMarks()));
                from.combination(joinSqlString);
                this.renderJoinCondition(joinSqlString, tableNode.getPojoMapping().getJoinCondition(),
                        tableNode.getBelongPojoTable() == null ? JdbcConstant.MAIN_TABLE_NAME : tableNode.getBelongPojoTable().tableAliasName(),
                        tableNode.tableAliasName(), dialect);
            }
        }
    }

    protected JoinSqlString buildJoinSqlString(JoinMode joinMode, NameSqlString nameSqlString) {
        switch (joinMode) {
            case LEFT_JOIN:
                return new LeftJoinSqlStringImpl(nameSqlString);
            case RIGHT_JOIN:
                return new RightJoinSqlStringImpl(nameSqlString);
            default: return new InnerJoinSqlStringImpl(nameSqlString);
        }
    }

    /**
     * 关联条件
     */
    protected void renderJoinCondition(JoinSqlString leftJoin, List<On> joinCondition, String originalTable, String joinTable, Dialect dialect) {
        for (On on : joinCondition) {
            leftJoin.combination(new JoinConditionSqlStringImpl(originalTable, on.getForeignKeyInfo().getDbFieldName(),
                    joinTable, on.getTargetFiledInfo().getDbFieldName(), dialect.getDbFieldQuotationMarks()));
        }
    }

    /**
     * 处理排序
     */
    protected OrderBySqlString buildOrderBy(OrderBy orderBy, FieldAccessor fieldAccessor) {
        if (null == orderBy) {
            return null;
        }
        String q = JdbcOperationSupport.getDialect().getDbFieldQuotationMarks();
        List<String> fields = new ArrayList<>(orderBy.getFields().size());
        for (String field : orderBy.getFields()) {
            FieldDescriptor fieldDescriptor = fieldAccessor.get(field);
            String fieldName = fieldDescriptor.getTableNode().tableAliasName() + "." + q + fieldDescriptor.getDbFieldName() + q;
            fields.add(fieldName);
        }
        return new OrderBySqlStringImpl(orderBy.isAsc(), fields);
    }
}
