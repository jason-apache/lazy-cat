package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/7/27 13:20
 */
public class FunctionArgumentException extends FunctionHandlerException {
    private static final long serialVersionUID = -7426030484863021063L;

    public FunctionArgumentException() {
    }

    public FunctionArgumentException(String message) {
        super(message);
    }

    public FunctionArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionArgumentException(Throwable cause) {
        super(cause);
    }

    public FunctionArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
