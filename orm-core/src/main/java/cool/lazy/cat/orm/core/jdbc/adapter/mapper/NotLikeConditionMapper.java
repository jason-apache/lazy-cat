package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotAllLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotLeftLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotRightLike;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.NotLikeConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.ConcatFunctionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/19 16:02
 */
public class NotLikeConditionMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof NotLike;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        if (type instanceof NotAllLike) {
            return new NotLikeConditionExpressionSqlString(((NotAllLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), "'%'", ":" + paramName, "'%'"));
        } else if (type instanceof NotLeftLike) {
            return new NotLikeConditionExpressionSqlString(((NotLeftLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), "'%'", ":" + paramName));
        } else {
            return new NotLikeConditionExpressionSqlString(((NotRightLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), ":" + paramName, "'%'"));
        }
    }
}
