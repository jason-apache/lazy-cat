package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/23 13:07
 */
public class CannotResolverConditionException extends BasicJdbcException {
    private static final long serialVersionUID = 4363708283893766846L;

    public CannotResolverConditionException() {
    }

    public CannotResolverConditionException(String message) {
        super(message);
    }

    public CannotResolverConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotResolverConditionException(Throwable cause) {
        super(cause);
    }

    public CannotResolverConditionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
