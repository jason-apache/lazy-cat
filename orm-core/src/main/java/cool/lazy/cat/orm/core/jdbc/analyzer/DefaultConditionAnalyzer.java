package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.adapter.ConditionTypeAdapter;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldDescriptor;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.condition.inject.SqlConditionValuePlaceHolder;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.CombinationType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionGroup;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionGroupImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/3/13 20:49
 */
public class DefaultConditionAnalyzer implements ConditionAnalyzer {

    protected final ConditionTypeAdapter conditionTypeAdapter;

    public DefaultConditionAnalyzer(ConditionTypeAdapter conditionTypeAdapter) {
        this.conditionTypeAdapter = conditionTypeAdapter;
    }

    @Override
    public WhereSqlString<?> analysis(Class<? extends SqlType> type, Class<?> pojoType, FieldAccessor fieldAccessor, SqlCondition sqlCondition) {
        if (sqlCondition.nested()) {
            // 处理嵌套的复杂条件
            WhereSqlString<ConditionGroup> whereSqlString = new WhereSqlStringImpl<>();
            ConditionGroup conditionGroup = new ConditionGroupImpl(CombinationType.NONE, true);
            this.recursionRender(type, whereSqlString, CombinationType.NONE, conditionGroup, fieldAccessor, sqlCondition);
            whereSqlString.combination(conditionGroup);
            return whereSqlString;
        } else {
            WhereSqlString<ConditionSqlString> whereSqlString = new WhereSqlStringImpl<>();
            this.render(type, whereSqlString, fieldAccessor, sqlCondition);
            return whereSqlString;
        }
    }

    protected void render(Class<? extends SqlType> type, WhereSqlString<ConditionSqlString> whereSqlString, FieldAccessor fieldAccessor, SqlCondition condition) {
        ConditionSqlString conditionSqlString = this.buildConditionSql(type, whereSqlString.incrementAndGetTotalCount(), CombinationType.NONE, fieldAccessor.get(condition.field()), condition);
        whereSqlString.combination(conditionSqlString);
        if (CollectionUtil.isNotEmpty(condition.getAnd())) {
            for (SqlCondition sqlCondition : condition.getAnd()) {
                whereSqlString.combination(this.buildConditionSql(type, whereSqlString.incrementAndGetTotalCount(), CombinationType.AND, fieldAccessor.get(sqlCondition.field()), sqlCondition));
            }
        }
        if (CollectionUtil.isNotEmpty(condition.getOr())) {
            for (SqlCondition sqlCondition : condition.getOr()) {
                whereSqlString.combination(this.buildConditionSql(type, whereSqlString.incrementAndGetTotalCount(), CombinationType.OR, fieldAccessor.get(sqlCondition.field()), sqlCondition));
            }
        }
    }

    /**
     * 注入参数名与参数值的映射
     * @param fieldAccessor 实体字段访问器
     * @param condition 条件
     */
    protected void recursionRender(Class<? extends SqlType> type, WhereSqlString<?> whereSqlString, CombinationType combinationType,
                                   ConditionGroup conditionGroup, FieldAccessor fieldAccessor, SqlCondition condition) {
        ConditionSqlString conditionSqlString = this.buildConditionSql(type, whereSqlString.incrementAndGetTotalCount(), combinationType, fieldAccessor.get(condition.field()), condition);
        ConditionGroup complexGroup = new ConditionGroupImpl(combinationType);
        complexGroup.combination(conditionSqlString);
        conditionGroup.combination(complexGroup);
        if (CollectionUtil.isNotEmpty(condition.getAnd())) {
            for (SqlCondition sqlCondition : condition.getAnd()) {
                ConditionGroup newConditionGroup = new ConditionGroupImpl(CombinationType.AND);
                complexGroup.combination(newConditionGroup);
                this.recursionRender(type, whereSqlString, CombinationType.AND, newConditionGroup, fieldAccessor, sqlCondition);
            }
        }
        if (CollectionUtil.isNotEmpty(condition.getOr())) {
            for (SqlCondition sqlCondition : condition.getOr()) {
                ConditionGroup newConditionGroup = new ConditionGroupImpl(CombinationType.OR);
                complexGroup.combination(newConditionGroup);
                this.recursionRender(type, whereSqlString, CombinationType.OR, newConditionGroup, fieldAccessor, sqlCondition);
            }
        }
    }

    protected ConditionSqlString buildConditionSql(Class<? extends SqlType> type, int offset, CombinationType combinationType, FieldDescriptor pojoField, SqlCondition condition) {
        String parameterName = condition.value() instanceof SqlConditionValuePlaceHolder ? condition.field()
                // 加入标识 避免与pojo字段名称发生碰撞
                : 0 + condition.field() + offset;
        ConditionExpressionSqlString expression = conditionTypeAdapter.adapt(condition.type(), parameterName, condition.value());
        if (null == expression) {
            return null;
        }
        String sql = Select.class.isAssignableFrom(type) ? pojoField.getTableNode().tableAliasName() + "." + pojoField.getDbFieldName() : pojoField.getDbFieldName();
        return new ConditionSqlStringImpl(sql, condition.type(), parameterName, condition.value(), combinationType, expression);
    }
}
