package cool.lazy.cat.orm.core.jdbc.constant;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.AllLike;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.Equals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.GreaterThan;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.GreaterThanEquals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.IsNull;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.LeftLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.LessThan;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.LessThanEquals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.None;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.NotEquals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.NotIn;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.NotNull;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotAllLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotLeftLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.NotRightLike;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.RightLike;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/19 16:04
 */
public final class ConditionConstant {

    public static final ConditionType EQUALS = new Equals();
    public static final ConditionType NOT_EQUALS = new NotEquals();
    public static final ConditionType LIKE = new AllLike();
    public static final ConditionType LEFT_LIKE = new LeftLike();
    public static final ConditionType RIGHT_LIKE = new RightLike();
    public static final ConditionType NOT_LIKE = new NotAllLike();
    public static final ConditionType NOT_LEFT_LIKE = new NotLeftLike();
    public static final ConditionType NOT_RIGHT_LIKE = new NotRightLike();
    public static final ConditionType IS_NULL = new IsNull();
    public static final ConditionType NOT_NULL = new NotNull();
    public static final ConditionType IN = new In();
    public static final ConditionType NOT_IN = new NotIn();
    public static final ConditionType GREATER_THAN = new GreaterThan();
    public static final ConditionType GREATER_THAN_EQUALS = new GreaterThanEquals();
    public static final ConditionType LESS_THAN = new LessThan();
    public static final ConditionType LESS_THAN_EQUALS = new LessThanEquals();
    public static final ConditionType NONE = new None();

    public static final Map<Class<? extends ConditionType>, ConditionType> CONDITION_TYPE_CACHE = new HashMap<>();

    static {
        CONDITION_TYPE_CACHE.put(EQUALS.getClass(), EQUALS);
        CONDITION_TYPE_CACHE.put(NOT_EQUALS.getClass(), NOT_EQUALS);
        CONDITION_TYPE_CACHE.put(LIKE.getClass(), LIKE);
        CONDITION_TYPE_CACHE.put(LEFT_LIKE.getClass(), LEFT_LIKE);
        CONDITION_TYPE_CACHE.put(RIGHT_LIKE.getClass(), RIGHT_LIKE);
        CONDITION_TYPE_CACHE.put(NOT_LIKE.getClass(), NOT_LIKE);
        CONDITION_TYPE_CACHE.put(NOT_LEFT_LIKE.getClass(), NOT_LEFT_LIKE);
        CONDITION_TYPE_CACHE.put(NOT_RIGHT_LIKE.getClass(), NOT_RIGHT_LIKE);
        CONDITION_TYPE_CACHE.put(IS_NULL.getClass(), IS_NULL);
        CONDITION_TYPE_CACHE.put(NOT_NULL.getClass(), NOT_NULL);
        CONDITION_TYPE_CACHE.put(IN.getClass(), IN);
        CONDITION_TYPE_CACHE.put(NOT_IN.getClass(), NOT_IN);
        CONDITION_TYPE_CACHE.put(GREATER_THAN.getClass(), GREATER_THAN);
        CONDITION_TYPE_CACHE.put(GREATER_THAN_EQUALS.getClass(), GREATER_THAN_EQUALS);
        CONDITION_TYPE_CACHE.put(LESS_THAN.getClass(), LESS_THAN);
        CONDITION_TYPE_CACHE.put(LESS_THAN_EQUALS.getClass(), LESS_THAN_EQUALS);
        CONDITION_TYPE_CACHE.put(NONE.getClass(), NONE);
    }
}
