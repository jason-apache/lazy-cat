package cool.lazy.cat.orm.core.jdbc.sql.condition.type.like;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;

/**
 * @author: mahao
 * @date: 2021/7/19 15:51
 */
public class AllLike extends Like implements ConditionType {

    @Override
    public String getSymbol() {
        return null;
    }

}
