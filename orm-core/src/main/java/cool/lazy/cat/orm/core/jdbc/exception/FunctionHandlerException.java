package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/7/27 12:57
 */
public abstract class FunctionHandlerException extends BasicJdbcException {
    private static final long serialVersionUID = 5252009842532791367L;

    public FunctionHandlerException() {
    }

    public FunctionHandlerException(String message) {
        super(message);
    }

    public FunctionHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionHandlerException(Throwable cause) {
        super(cause);
    }

    public FunctionHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
