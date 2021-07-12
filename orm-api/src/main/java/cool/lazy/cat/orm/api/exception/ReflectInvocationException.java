package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/8 12:56
 */
public class ReflectInvocationException extends ApiMethodInvocationException {
    private static final long serialVersionUID = -9037550689215496756L;

    public ReflectInvocationException() {
    }

    public ReflectInvocationException(String message) {
        super(message);
    }

    public ReflectInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectInvocationException(Throwable cause) {
        super(cause);
    }

    public ReflectInvocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
