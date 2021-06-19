package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/13 20:35
 */
public class CannotFindJoinConditionException extends BasicJdbcException {
    private static final long serialVersionUID = -4137798537002344516L;

    public CannotFindJoinConditionException() {
    }

    public CannotFindJoinConditionException(String message) {
        super(message);
    }

    public CannotFindJoinConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindJoinConditionException(Throwable cause) {
        super(cause);
    }

    public CannotFindJoinConditionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
