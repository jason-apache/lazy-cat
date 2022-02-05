package cool.lazy.cat.orm.core.jdbc.sql.string.condition.express;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotLike;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 13:54
 */
public class NotLikeConditionExpressionSqlString extends AbstractSqlString implements ConditionExpressionSqlString, SqlString {

    protected final Class<? extends NotLike> type;
    private final FunctionSqlString function;

    public NotLikeConditionExpressionSqlString(Class<? extends NotLike> type, FunctionSqlString function) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().not() + " " + JdbcOperationSupport.getDialect().getKeywordDictionary().like());
        this.type = type;
        this.function = function;
    }

    @Override
    public String getExpression() {
        return this.getVal();
    }

    @Override
    public ParameterNameSqlString getParameterName() {
        return function.getParameterName();
    }

    @Override
    public boolean paperJam() {
        return function.paperJam();
    }

    @Override
    public Cause cause() {
        return function.cause();
    }

    @Override
    public String toString() {
        return super.toString() + this.joiner().linkToNext(function) + function.joiner().linkToPre(this) + function;
    }
}
