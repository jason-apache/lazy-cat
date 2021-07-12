package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/8 11:31
 */
public abstract class ApiMethodInvocationException extends ApiException {
    private static final long serialVersionUID = 6171806757846589075L;

    public ApiMethodInvocationException() {
    }

    public ApiMethodInvocationException(String message) {
        super(message);
    }

    public ApiMethodInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiMethodInvocationException(Throwable cause) {
        super(cause);
    }

    public ApiMethodInvocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
