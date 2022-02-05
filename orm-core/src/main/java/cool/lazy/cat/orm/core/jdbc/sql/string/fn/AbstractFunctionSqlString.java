package cool.lazy.cat.orm.core.jdbc.sql.string.fn;

import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.InitializationRequiredSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 10:49
 */
public abstract class AbstractFunctionSqlString extends AbstractSqlString implements FunctionSqlString, InitializationRequiredSqlString {

    protected final String functionName;
    protected final ParameterNameSqlString parameterName;
    protected final Object[] args;
    protected boolean initialization;
    protected String payload;

    protected AbstractFunctionSqlString(String functionName, ParameterNameSqlString parameterName, Object[] args) {
        super(functionName);
        this.functionName = functionName;
        this.parameterName = parameterName;
        this.args = args;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public ParameterNameSqlString getParameterName() {
        return parameterName;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public boolean initialized() {
        return initialization;
    }

    @Override
    public void setInitialization(boolean initialization) {
        this.initialization = initialization;
    }

    @Override
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return this.payload;
    }
}
