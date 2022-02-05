package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/10/13 20:19
 */
public class DMLSqlStructureImpl implements DMLSqlStructure {

    private BehaviorDescriptor behaviorDescriptor;
    private WhereSqlString<?> whereSqlString;
    private List<SqlString> sqlStrings;
    private SqlCondition condition;

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
    public WhereSqlString<?> getWhere() {
        return whereSqlString;
    }

    @Override
    public void setWhere(WhereSqlString<?> where) {
        this.whereSqlString = where;
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
