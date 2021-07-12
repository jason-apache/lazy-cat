package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/12 13:18
 */
public class UnableToAdaptResponseDataWriterException extends ApiMethodInvocationException {
    private static final long serialVersionUID = 7854638457373241185L;

    public UnableToAdaptResponseDataWriterException() {
    }

    public UnableToAdaptResponseDataWriterException(String message) {
        super(message);
    }

    public UnableToAdaptResponseDataWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToAdaptResponseDataWriterException(Throwable cause) {
        super(cause);
    }

    public UnableToAdaptResponseDataWriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
