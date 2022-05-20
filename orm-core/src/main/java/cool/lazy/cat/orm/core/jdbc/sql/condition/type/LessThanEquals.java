package cool.lazy.cat.orm.core.jdbc.sql.condition.type;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;

/**
 * @author: mahao
 * @date: 2021/7/19 15:53
 */
public class LessThanEquals implements ConditionType, KeywordSymbol {

    @Override
    public String getSymbol() {
        return "<=";
    }
}
