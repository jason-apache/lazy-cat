package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.AllLike;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.LeftLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.Like;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.RightLike;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.LikeConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.ConcatFunctionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/19 16:02
 */
public class LikeConditionMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof Like;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        if (type instanceof AllLike) {
            return new LikeConditionExpressionSqlString(((AllLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), "'%'", ":" + paramName, "'%'"));
        } else if (type instanceof LeftLike) {
            return new LikeConditionExpressionSqlString(((LeftLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), "'%'", ":" + paramName));
        } else {
            return new LikeConditionExpressionSqlString(((RightLike) type).getClass(), new ConcatFunctionSqlString(new ParameterNameSqlStringImpl(paramName), ":" + paramName, "'%'"));
        }
    }
}
