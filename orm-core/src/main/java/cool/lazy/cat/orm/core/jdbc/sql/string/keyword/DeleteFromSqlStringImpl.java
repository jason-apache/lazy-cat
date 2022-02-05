package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:04
 */
public class DeleteFromSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements DeleteFromSqlString {

    private final NameSqlString nameSqlString;

    public DeleteFromSqlStringImpl(NameSqlString nameSqlString) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().delete() + " " + JdbcOperationSupport.getDialect().getKeywordDictionary().from());
        this.nameSqlString = nameSqlString;
        this.combination(nameSqlString);
    }

    @Override
    public NameSqlString getObjectNameSql() {
        return nameSqlString;
    }
}
