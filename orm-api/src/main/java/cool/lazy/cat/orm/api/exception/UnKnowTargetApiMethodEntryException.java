package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/13 16:04
 */
public class UnKnowTargetApiMethodEntryException extends ApiMethodInvocationException {
    private static final long serialVersionUID = 3969200446694067677L;

    public UnKnowTargetApiMethodEntryException() {
    }

    public UnKnowTargetApiMethodEntryException(String message) {
        super(message);
    }

    public UnKnowTargetApiMethodEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowTargetApiMethodEntryException(Throwable cause) {
        super(cause);
    }

    public UnKnowTargetApiMethodEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
