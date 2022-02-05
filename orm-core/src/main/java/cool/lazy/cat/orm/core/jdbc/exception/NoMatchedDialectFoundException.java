package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/7/25 14:11
 */
public class NoMatchedDialectFoundException extends BasicJdbcException {
    private static final long serialVersionUID = -2171630795546744850L;

    public NoMatchedDialectFoundException() {
    }

    public NoMatchedDialectFoundException(String message) {
        super(message);
    }

    public NoMatchedDialectFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchedDialectFoundException(Throwable cause) {
        super(cause);
    }

    public NoMatchedDialectFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
