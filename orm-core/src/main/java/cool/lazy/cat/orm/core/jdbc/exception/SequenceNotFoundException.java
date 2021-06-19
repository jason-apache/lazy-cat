package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/4/19 09:13
 */
public class SequenceNotFoundException extends BasicJdbcException {
    private static final long serialVersionUID = 7466351450644947379L;

    public SequenceNotFoundException() {
    }

    public SequenceNotFoundException(String message) {
        super(message);
    }

    public SequenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceNotFoundException(Throwable cause) {
        super(cause);
    }

    public SequenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
