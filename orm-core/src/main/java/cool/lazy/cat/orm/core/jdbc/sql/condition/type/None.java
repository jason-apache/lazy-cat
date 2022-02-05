package cool.lazy.cat.orm.core.jdbc.sql.condition.type;

/**
 * @author: mahao
 * @date: 2021/7/19 15:49
 */
public class None implements ConditionType {
    @Override
    public String getSymbol() {
        return null;
    }
}
