package cool.lazy.cat.orm.core.jdbc.sql.printer.cause;

import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/16 11:24
 */
public class SqlConditionParameterNotMappedCause implements Cause {

    private final ConditionSqlString conditionSqlString;

    public SqlConditionParameterNotMappedCause(ConditionSqlString conditionSqlString) {
        this.conditionSqlString = conditionSqlString;
    }

    @Override
    public ConditionSqlString getSqlString() {
        return conditionSqlString;
    }
}
