package cool.lazy.cat.orm.core.jdbc.sql.condition.type;

/**
 * @author: mahao
 * @date: 2021/7/19 15:50
 */
public class Equals implements ConditionType, KeywordSymbol {

    @Override
    public String getSymbol() {
        return "=";
    }
}
