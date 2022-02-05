package cool.lazy.cat.orm.core.jdbc.sql.dialect;

import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.FunctionHandler;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 12:48
 */
public abstract class AbstractDialect implements Dialect {

    protected final FunctionHandlerAdapter functionHandlerAdapter;
    protected AbstractDialect(FunctionHandlerAdapter functionHandlerAdapter) {
        this.functionHandlerAdapter = functionHandlerAdapter;
    }

    @Override
    public void handleFunctionSqlString(FunctionSqlString functionSqlString) {
        FunctionHandler handler = functionHandlerAdapter.adapt(this.getDataBaseDriver(), functionSqlString.getFunctionName());
        handler.handle(functionSqlString);
    }
}
