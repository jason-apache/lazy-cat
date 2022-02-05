package cool.lazy.cat.orm.core.jdbc.exception.executor;

/**
 * @author: mahao
 * @date: 2021/7/16 14:18
 */
public class InsolubleCauseException extends PrinterSqlException {
    private static final long serialVersionUID = 3197918289325183479L;

    public InsolubleCauseException() {
    }

    public InsolubleCauseException(String message) {
        super(message);
    }

    public InsolubleCauseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsolubleCauseException(Throwable cause) {
        super(cause);
    }

    public InsolubleCauseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
