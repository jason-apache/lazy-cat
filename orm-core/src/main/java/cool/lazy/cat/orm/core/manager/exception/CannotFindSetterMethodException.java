package cool.lazy.cat.orm.core.manager.exception;

/**
 * @author: mahao
 * @date: 2021/3/10 12:39
 */
public class CannotFindSetterMethodException extends BasicManagerException {

    private static final long serialVersionUID = 4336593269350194565L;

    public CannotFindSetterMethodException() {
    }

    public CannotFindSetterMethodException(String message) {
        super(message);
    }

    public CannotFindSetterMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindSetterMethodException(Throwable cause) {
        super(cause);
    }

    public CannotFindSetterMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
