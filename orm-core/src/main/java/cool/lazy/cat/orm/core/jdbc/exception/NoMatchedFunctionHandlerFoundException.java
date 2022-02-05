package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/7/27 13:22
 */
public class NoMatchedFunctionHandlerFoundException extends FunctionHandlerException {
    private static final long serialVersionUID = -5226575455910853915L;

    public NoMatchedFunctionHandlerFoundException() {
    }

    public NoMatchedFunctionHandlerFoundException(String message) {
        super(message);
    }

    public NoMatchedFunctionHandlerFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchedFunctionHandlerFoundException(Throwable cause) {
        super(cause);
    }

    public NoMatchedFunctionHandlerFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
