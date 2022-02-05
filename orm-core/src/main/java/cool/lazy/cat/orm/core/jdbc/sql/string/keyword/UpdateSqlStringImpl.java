package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:08
 */
public class UpdateSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements UpdateSqlString {

    private final NameSqlString nameSqlString;

    public UpdateSqlStringImpl(NameSqlString nameSqlString) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().update());
        this.nameSqlString = nameSqlString;
        this.combination(nameSqlString);
    }

    @Override
    public NameSqlString getObjectNameSql() {
        return nameSqlString;
    }
}
