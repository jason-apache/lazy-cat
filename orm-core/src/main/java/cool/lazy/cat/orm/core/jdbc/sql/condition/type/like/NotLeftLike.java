package cool.lazy.cat.orm.core.jdbc.sql.condition.type.like;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;

/**
 * @author: mahao
 * @date: 2022-01-25 14:11
 */
public class NotLeftLike extends NotLike implements ConditionType {

    @Override
    public String getSymbol() {
        return null;
    }
}
