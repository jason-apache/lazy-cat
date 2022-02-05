package cool.lazy.cat.orm.core.jdbc.sql.string.fn;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/25 17:17
 */
public class CountFunctionSqlString extends AbstractFunctionSqlString implements FunctionSqlString, SqlString {

    public CountFunctionSqlString(String... fields) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().count(), null, fields);
    }
}
