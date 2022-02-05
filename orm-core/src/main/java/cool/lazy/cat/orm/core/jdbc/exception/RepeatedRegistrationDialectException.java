package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/7/25 14:10
 */
public class RepeatedRegistrationDialectException extends BasicJdbcException {
    private static final long serialVersionUID = -8833510159938411062L;

    public RepeatedRegistrationDialectException() {
    }

    public RepeatedRegistrationDialectException(String message) {
        super(message);
    }

    public RepeatedRegistrationDialectException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatedRegistrationDialectException(Throwable cause) {
        super(cause);
    }

    public RepeatedRegistrationDialectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
