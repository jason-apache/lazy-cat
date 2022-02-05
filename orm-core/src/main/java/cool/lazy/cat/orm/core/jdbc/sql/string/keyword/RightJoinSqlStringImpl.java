package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/13 14:41
 */
public class RightJoinSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements JoinSqlString {

    private final NameSqlString nameSqlString;

    public RightJoinSqlStringImpl(NameSqlString nameSqlString) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().right() + " " + JdbcOperationSupport.getDialect().getKeywordDictionary().join());
        this.nameSqlString = nameSqlString;
        this.combination(nameSqlString);
    }

    @Override
    public NameSqlString getObjectNameSql() {
        return nameSqlString;
    }
}
