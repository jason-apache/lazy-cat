package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/12 13:07
 */
public class JsonReadException extends ApiMethodInvocationException {
    private static final long serialVersionUID = -2280688469043193469L;

    public JsonReadException() {
    }

    public JsonReadException(String message) {
        super(message);
    }

    public JsonReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonReadException(Throwable cause) {
        super(cause);
    }

    public JsonReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
