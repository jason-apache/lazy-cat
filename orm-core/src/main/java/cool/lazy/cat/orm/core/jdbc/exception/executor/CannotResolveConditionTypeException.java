package cool.lazy.cat.orm.core.jdbc.exception.executor;

/**
 * @author: mahao
 * @date: 2021/7/19 16:23
 */
public class CannotResolveConditionTypeException extends PrinterSqlException {
    private static final long serialVersionUID = -1719047426623844938L;

    public CannotResolveConditionTypeException() {
    }

    public CannotResolveConditionTypeException(String message) {
        super(message);
    }

    public CannotResolveConditionTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotResolveConditionTypeException(Throwable cause) {
        super(cause);
    }

    public CannotResolveConditionTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
