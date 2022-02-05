package cool.lazy.cat.orm.core.jdbc.exception.executor;


/**
 * @author: mahao
 * @date: 2021/7/16 13:50
 */
public class MismatchedSqlTypeException extends SqlException {
    private static final long serialVersionUID = 3944294571420051351L;

    public MismatchedSqlTypeException() {
    }

    public MismatchedSqlTypeException(String message) {
        super(message);
    }

    public MismatchedSqlTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchedSqlTypeException(Throwable cause) {
        super(cause);
    }

    public MismatchedSqlTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
