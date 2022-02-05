package cool.lazy.cat.orm.core.jdbc.sql.printer.cause;

import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 11:22
 */
public class FunctionDialectNotInitCause implements NotInitCause {

    private final FunctionSqlString functionSqlString;

    public FunctionDialectNotInitCause(FunctionSqlString functionSqlString) {
        this.functionSqlString = functionSqlString;
    }

    @Override
    public FunctionSqlString getSqlString() {
        return functionSqlString;
    }
}
