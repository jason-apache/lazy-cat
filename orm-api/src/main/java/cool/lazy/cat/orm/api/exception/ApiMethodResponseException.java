package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/12 13:08
 */
public class ApiMethodResponseException extends ApiMethodInvocationException {
    private static final long serialVersionUID = 1724549771008294041L;

    public ApiMethodResponseException() {
    }

    public ApiMethodResponseException(String message) {
        super(message);
    }

    public ApiMethodResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiMethodResponseException(Throwable cause) {
        super(cause);
    }

    public ApiMethodResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
