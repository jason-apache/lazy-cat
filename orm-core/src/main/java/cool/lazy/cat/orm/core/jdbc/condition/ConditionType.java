package cool.lazy.cat.orm.core.jdbc.condition;

/**
 * @author: mahao
 * @date: 2021/3/16 14:39
 * 条件
 */
public enum ConditionType {

    NONE(""),
    EQUALS("="),
    IN("IN"),
    NOT_IN("NOT IN"),
    NOT_EQUALS("!="),
    ALL_LIKE("'%'"),
    LEFT_LIKE("'%'"),
    RIGHT_LIKE("'%'"),
    LESS_THAN("<"),
    LESS_THAN_EQUALS("<="),
    GREATER_THAN(">"),
    GREATER_THAN_EQUALS(">="),
    IS_NULL ("IS NULL"),
    NOT_NULL ("IS NOT NULL"),
    ;

    private final String symbol;

    ConditionType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
