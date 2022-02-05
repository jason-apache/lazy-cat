package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/31 20:24
 */
public class ValidationException extends BasicJdbcException {
    private static final long serialVersionUID = 31532234967623873L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
