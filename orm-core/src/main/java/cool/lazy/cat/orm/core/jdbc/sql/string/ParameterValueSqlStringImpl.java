package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.FieldSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2022-01-25 11:17
 */
public class ParameterValueSqlStringImpl implements ParameterValueSqlString {

    private final ParameterNameSqlString parameterName;

    public ParameterValueSqlStringImpl(ParameterNameSqlString parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public ParameterNameSqlString getParameterName() {
        return parameterName;
    }

    @Override
    public String toString() {
        return this.getSymbol() + parameterName;
    }

    @Override
    public SqlStringJoiner joiner() {
        return new FieldSqlStringJoiner();
    }
}
