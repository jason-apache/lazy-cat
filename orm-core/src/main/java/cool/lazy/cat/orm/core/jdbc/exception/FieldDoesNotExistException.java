package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/4/19 09:13
 */
public class FieldDoesNotExistException extends BasicJdbcException {
    private static final long serialVersionUID = 7466351450644947379L;

    public FieldDoesNotExistException() {
    }

    public FieldDoesNotExistException(String message) {
        super(message);
    }

    public FieldDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public FieldDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
