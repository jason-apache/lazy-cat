package cool.lazy.cat.orm.core.jdbc.sql.condition.type;

/**
 * @author: mahao
 * @date: 2021/7/19 15:53
 */
public class LessThan implements ConditionType, KeywordSymbol {

    @Override
    public String getSymbol() {
        return "<";
    }
}
