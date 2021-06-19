package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/3/14 12:54
 */
public class BadRequestException extends ApiException {
    private static final long serialVersionUID = -5680537344076056249L;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
