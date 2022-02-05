package cool.lazy.cat.orm.core.jdbc.sql.string.condition.express;

import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 13:51
 */
public class ConditionExpressionSqlStringImpl implements ConditionExpressionSqlString, SqlString {

    private final String expression;
    private final ParameterNameSqlString parameterName;

    public ConditionExpressionSqlStringImpl(String expression, ParameterNameSqlString parameterName) {
        this.expression = expression;
        this.parameterName = parameterName;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public ParameterNameSqlString getParameterName() {
        return parameterName;
    }

    @Override
    public String toString() {
        if (null == parameterName) {
            return expression;
        }
        return expression + this.joiner().linkToNext(parameterName) + parameterName.joiner().linkToPre(this) + parameterName;
    }
}
