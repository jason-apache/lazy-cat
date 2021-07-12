package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/12 13:07
 */
public class JsonFormatException extends ApiMethodInvocationException {
    private static final long serialVersionUID = -2280688469043193469L;

    public JsonFormatException() {
    }

    public JsonFormatException(String message) {
        super(message);
    }

    public JsonFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonFormatException(Throwable cause) {
        super(cause);
    }

    public JsonFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
