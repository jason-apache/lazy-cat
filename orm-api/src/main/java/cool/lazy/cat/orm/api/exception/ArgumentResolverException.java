package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/8 12:58
 */
public class ArgumentResolverException extends ParameterInjectorException {
    private static final long serialVersionUID = -4950809023015052202L;

    public ArgumentResolverException() {
    }

    public ArgumentResolverException(String message) {
        super(message);
    }

    public ArgumentResolverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentResolverException(Throwable cause) {
        super(cause);
    }

    public ArgumentResolverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
