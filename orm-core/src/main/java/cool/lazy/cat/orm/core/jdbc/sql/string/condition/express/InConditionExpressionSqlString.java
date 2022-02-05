package cool.lazy.cat.orm.core.jdbc.sql.string.condition.express;

import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SwitchSymbolSqlString;

/**
 * @author: mahao
 * @date: 2022-01-25 10:57
 */
public class InConditionExpressionSqlString implements ConditionExpressionSqlString, SwitchSymbolSqlString {

    private final String keyword;
    private final ParameterNameSqlString parameterName;

    public InConditionExpressionSqlString(String keyword, ParameterNameSqlString parameterName) {
        this.keyword = keyword;
        this.parameterName = parameterName;
    }

    @Override
    public String getOpenOperator() {
        return "(";
    }

    @Override
    public String getCloseOperator() {
        return ")";
    }

    @Override
    public String getExpression() {
        return keyword;
    }

    @Override
    public ParameterNameSqlString getParameterName() {
        return parameterName;
    }

    @Override
    public String toString() {
        return keyword + " " + this.getOpenOperator() + this.joiner().linkToNext(parameterName) + parameterName.joiner().linkToPre(this) + parameterName + this.getCloseOperator();
    }
}
