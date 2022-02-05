package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.FromSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.OrderBySqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/10/13 15:35
 */
public class DQLSqlStructureImpl implements DQLSqlStructure {

    private BehaviorDescriptor behaviorDescriptor;
    private FromSqlString fromSqlString;
    private WhereSqlString<?> whereSqlString;
    private OrderBySqlString orderBySqlString;
    private List<SqlString> sqlStrings;
    private SqlCondition condition;

    public DQLSqlStructureImpl() {
    }

    public DQLSqlStructureImpl(BehaviorDescriptor behaviorDescriptor) {
        this.behaviorDescriptor = behaviorDescriptor;
    }

    @Override
    public void setBehaviorDescriptor(BehaviorDescriptor behaviorDescriptor) {
        this.behaviorDescriptor = behaviorDescriptor;
    }

    @Override
    public BehaviorDescriptor getBehaviorDescriptor() {
        return behaviorDescriptor;
    }

    @Override
    public void addSqlString(SqlString sql) {
        if (null == this.sqlStrings) {
            this.sqlStrings = new ArrayList<>(300);
        }
        this.sqlStrings.add(sql);
    }

    @Override
    public void insertSqlString(int index, SqlString sqlString) {
        if (null == this.sqlStrings) {
            throw new IllegalArgumentException("空的sqlString集合, 无法插入至指定位置: " + index);
        }
        this.sqlStrings.add(index, sqlString);
    }

    @Override
    public Collection<SqlString> getSqlStrings() {
        return sqlStrings;
    }

    @Override
    public FromSqlString getFrom() {
        return fromSqlString;
    }

    @Override
    public void setFromSqlString(FromSqlString fromSqlString) {
        this.fromSqlString = fromSqlString;
    }

    @Override
    public WhereSqlString<?> getWhere() {
        return whereSqlString;
    }

    @Override
    public void setWhere(WhereSqlString<?> where) {
        this.whereSqlString = where;
    }

    @Override
    public OrderBySqlString getOrderBy() {
        return orderBySqlString;
    }

    @Override
    public void setOrderBy(OrderBySqlString orderBy) {
        this.orderBySqlString = orderBy;
    }

    @Override
    public void reOrderWhere() {
        if (null == whereSqlString) {
            return;
        }
        if (null == orderBySqlString) {
            return;
        }
        this.sqlStrings.remove(whereSqlString);
        this.sqlStrings.add(this.sqlStrings.indexOf(orderBySqlString), whereSqlString);
    }

    @Override
    public SqlCondition getCondition() {
        return condition;
    }

    @Override
    public void setCondition(SqlCondition condition) {
        this.condition = condition;
    }
}
