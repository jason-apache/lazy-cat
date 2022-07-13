package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/11 19:03
 */
public class GetConnectionException extends BasicGeneratorException {
    private static final long serialVersionUID = -6746156694035366530L;

    public GetConnectionException() {
    }

    public GetConnectionException(String message) {
        super(message);
    }

    public GetConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetConnectionException(Throwable cause) {
        super(cause);
    }

    public GetConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
