package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:13
 */
public class ValuesSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements ValuesSqlString {

    public ValuesSqlStringImpl() {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().values());
    }

    @Override
    public String getOpenOperator() {
        return "(";
    }

    @Override
    public String getCloseOperator() {
        return ")";
    }
}
