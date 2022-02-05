package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.analyzer.ConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.CombinationType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.structure.SqlStructure;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/19 12:32
 * sql条件打印机
 */
public class ConditionSqlPrinter extends AbstractSqlPrinter implements SqlPrinter {

    protected final PojoTableManager pojoTableManager;
    protected final ConditionAnalyzer conditionAnalyzer;

    public ConditionSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager, ConditionAnalyzer conditionAnalyzer) {
        super(correctorExecutor);
        this.pojoTableManager = pojoTableManager;
        this.conditionAnalyzer = conditionAnalyzer;
    }

    @Override
    public boolean support(SqlParameterMapping sqlParameterMapping) {
        if (null != sqlParameterMapping.getParam().getCondition()) {
            return true;
        }
        if (null != sqlParameterMapping.getDataOperationDescriptor()) {
            DataOperationDescriptor operationDescriptor = sqlParameterMapping.getDataOperationDescriptor();
            if (CollectionUtil.isNotEmpty(operationDescriptor.getUpdateData())) {
                if (operationDescriptor.getUpdateData().stream().anyMatch(p -> null != p.getCondition())) {
                    return true;
                }
            }
            if (CollectionUtil.isNotEmpty(operationDescriptor.getDeleteData())) {
                if (operationDescriptor.getDeleteData().stream().anyMatch(p -> null != p.getCondition())) {
                    return true;
                }
            }
            if (CollectionUtil.isNotEmpty(operationDescriptor.getLogicDeleteData())) {
                if (operationDescriptor.getLogicDeleteData().stream().anyMatch(p -> null != p.getCondition())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void printTo(SqlParameterMapping sqlParameterMapping) {
        if (null == sqlParameterMapping.getParameterMappings()) {
            return;
        }
        Param param = sqlParameterMapping.getParam();
        if (param instanceof SearchParam) {
            this.processSearchCondition(sqlParameterMapping);
        } else {
            this.processDMLCondition(sqlParameterMapping);
        }
    }

    /**
     * 处理查询条件
     */
    protected void processSearchCondition(SqlParameterMapping sqlParameterMapping) {
        SearchParam<?> param = Caster.cast(sqlParameterMapping.getParam());
        FieldAccessor fieldAccessor = param.getFieldAccessor();
        fieldAccessor.init();
        SqlCondition condition = param.getCondition();
        WhereSqlString<?> analysisWhere = conditionAnalyzer.analysis(sqlParameterMapping.getType(), param.getPojoType(), fieldAccessor, condition);
        for (ParameterMapping currentlyProcessed : sqlParameterMapping.getParameterMappings()) {
            SqlStructure sqlStructure = currentlyProcessed.getSqlStructure();
            this.merge(analysisWhere, sqlStructure, sqlParameterMapping, currentlyProcessed);
        }
    }

    /**
     * 处理数据操纵条件
     */
    protected void processDMLCondition(SqlParameterMapping sqlParameterMapping) {
        // 缓存构建的条件sql 避免重复计算
        Map<SqlCondition, WhereSqlString<?>> conditionWhereSqlStringMap = new HashMap<>(sqlParameterMapping.getParameterMappings().size());
        for (ParameterMapping currentlyProcessed : sqlParameterMapping.getParameterMappings()) {
            Class<?> pojoType = currentlyProcessed.getPojoType();
            SqlStructure sqlStructure = currentlyProcessed.getSqlStructure();
            SqlCondition condition = sqlStructure.getCondition();
            if (condition == null) {
                condition = sqlParameterMapping.getParam().getCondition();
            }
            if (condition == null) {
                continue;
            }
            WhereSqlString<?> whereSqlString = conditionWhereSqlStringMap.get(condition);
            if (null == whereSqlString) {
                this.check(condition);
                FieldAccessor fieldAccessor = pojoTableManager.getByPojoType(pojoType).getTableInfo().getFieldMapper().getFieldAccessor();
                whereSqlString = conditionAnalyzer.analysis(sqlParameterMapping.getType(), pojoType, fieldAccessor, condition);
                conditionWhereSqlStringMap.put(condition, whereSqlString);
            }
            this.merge(whereSqlString, sqlStructure, sqlParameterMapping, currentlyProcessed);
        }
    }

    private void check(SqlCondition condition) {
        List<SqlCondition> conditions = condition.flatCondition();
        for (SqlCondition sqlCondition : conditions) {
            if (sqlCondition.field().contains(".")) {
                throw new UnsupportedOperationException("不支持嵌套的过滤条件: " + sqlCondition.field());
            }
        }
    }

    protected void merge(WhereSqlString<?> analysisWhere, SqlStructure sqlStructure, SqlParameterMapping sqlParameterMapping, ParameterMapping currentlyProcessed) {
        if (sqlStructure.getWhere() != null) {
            WhereSqlString<?> where = sqlStructure.getWhere();
            sqlStructure.getSqlStrings().remove(where);
            // 合并条件 重新添加
            this.merge(Caster.cast(analysisWhere.getContent()), Caster.cast(where.getContent()));
            super.renderSqlString(where, sqlParameterMapping, currentlyProcessed);
        } else {
            super.renderSqlString(analysisWhere, sqlParameterMapping, currentlyProcessed);
            sqlStructure.setWhere(analysisWhere);
        }
        // 重排序 whereSql
        sqlStructure.reOrderWhere();
    }

    protected void merge(Collection<ConditionSqlString> c1, Collection<ConditionSqlString> c2) {
        if (CollectionUtil.isNotEmpty(c1) && CollectionUtil.isNotEmpty(c2)) {
            ConditionSqlString conditionSqlString = c1.iterator().next();
            conditionSqlString.setCombinationType(CombinationType.AND);
            c2.addAll(c1);
        }
    }
}