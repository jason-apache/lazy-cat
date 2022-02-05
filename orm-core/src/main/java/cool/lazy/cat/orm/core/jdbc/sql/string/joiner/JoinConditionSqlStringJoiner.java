package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.JoinConditionSqlString;

/**
 * @author: mahao
 * @date: 2021/10/13 18:55
 * 联查条件连接条件
 */
public class JoinConditionSqlStringJoiner implements SqlStringJoiner {

    @Override
    public String linkToPre(SqlString preSql) {
        if (preSql instanceof JoinConditionSqlString) {
            return JdbcOperationSupport.getDialect().getKeywordDictionary().and() + " ";
        }
        return JdbcOperationSupport.getDialect().getKeywordDictionary().on() + " ";
    }

    @Override
    public String linkToNext(SqlString nextSql) {
        return " ";
    }
}
