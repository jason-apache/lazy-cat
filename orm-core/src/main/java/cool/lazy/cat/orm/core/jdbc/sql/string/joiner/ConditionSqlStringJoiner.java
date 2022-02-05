package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.CombinationType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;

/**
 * @author: mahao
 * @date: 2021/10/12 20:26
 * 条件与条件之间的关系符号
 */
public class ConditionSqlStringJoiner implements SqlStringJoiner {

    private final CombinationType combinationType;

    public ConditionSqlStringJoiner(CombinationType combinationType) {
        this.combinationType = combinationType;
    }

    @Override
    public String linkToPre(SqlString preSql) {
        if (preSql instanceof ConditionSqlString) {
            switch (combinationType) {
                case AND: return JdbcOperationSupport.getDialect().getKeywordDictionary().and() + " ";
                case OR: return JdbcOperationSupport.getDialect().getKeywordDictionary().or() + " ";
                default:
            }
        }
        return "";
    }

    @Override
    public String linkToNext(SqlString nextSql) {
        return " ";
    }
}
