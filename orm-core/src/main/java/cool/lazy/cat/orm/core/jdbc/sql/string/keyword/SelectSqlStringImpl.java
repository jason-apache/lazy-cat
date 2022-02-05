package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:14
 */
public class SelectSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements SelectSqlString {

    public SelectSqlStringImpl() {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().select());
    }
}
