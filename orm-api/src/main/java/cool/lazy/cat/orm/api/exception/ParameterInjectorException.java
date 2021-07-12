package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/8 12:57
 */
public abstract class ParameterInjectorException extends ApiMethodInvocationException {
    private static final long serialVersionUID = 2954859498816325076L;

    public ParameterInjectorException() {
    }

    public ParameterInjectorException(String message) {
        super(message);
    }

    public ParameterInjectorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterInjectorException(Throwable cause) {
        super(cause);
    }

    public ParameterInjectorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
