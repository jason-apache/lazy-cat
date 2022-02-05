package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/13 21:36
 */
public class SetSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements SetSqlString {

    public SetSqlStringImpl() {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().set());
    }
}
