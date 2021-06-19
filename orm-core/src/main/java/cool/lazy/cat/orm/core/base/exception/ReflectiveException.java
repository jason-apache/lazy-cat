package cool.lazy.cat.orm.core.base.exception;

/**
 * @author: mahao
 * @date: 2021/3/31 18:51
 */
public class ReflectiveException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = -194907186300977449L;

    public ReflectiveException() {
    }

    public ReflectiveException(String message) {
        super(message);
    }

    public ReflectiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectiveException(Throwable cause) {
        super(cause);
    }

    public ReflectiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
