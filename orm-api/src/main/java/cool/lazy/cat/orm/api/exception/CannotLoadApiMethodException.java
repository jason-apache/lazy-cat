package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/12 13:30
 */
public class CannotLoadApiMethodException extends ApiException {
    private static final long serialVersionUID = 8611693187141216694L;

    public CannotLoadApiMethodException() {
    }

    public CannotLoadApiMethodException(String message) {
        super(message);
    }

    public CannotLoadApiMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotLoadApiMethodException(Throwable cause) {
        super(cause);
    }

    public CannotLoadApiMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
