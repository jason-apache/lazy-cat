package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/17 15:48
 */
public class UnKnowFiledException extends BasicJdbcException {
    private static final long serialVersionUID = 8159824004763398706L;

    public UnKnowFiledException() {
    }

    public UnKnowFiledException(String message) {
        super(message);
    }

    public UnKnowFiledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowFiledException(Throwable cause) {
        super(cause);
    }

    public UnKnowFiledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
