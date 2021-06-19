package cool.lazy.cat.orm.core.manager.exception;

/**
 * @author: mahao
 * @date: 2021/3/10 12:58
 */
public class NotStandardSetterMethodException extends BasicManagerException {

    private static final long serialVersionUID = -1614698217519843392L;

    public NotStandardSetterMethodException() {
    }

    public NotStandardSetterMethodException(String message) {
        super(message);
    }

    public NotStandardSetterMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotStandardSetterMethodException(Throwable cause) {
        super(cause);
    }

    public NotStandardSetterMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
