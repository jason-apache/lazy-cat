package cool.lazy.cat.orm.core.jdbc.sql.string.fn;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 14:01
 */
public class ConcatFunctionSqlString extends AbstractFunctionSqlString implements FunctionSqlString, SqlString {

    public ConcatFunctionSqlString(ParameterNameSqlString parameterName, String... args) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().concat(), parameterName, args);
    }
}
