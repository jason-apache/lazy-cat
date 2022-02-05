package cool.lazy.cat.orm.core.jdbc.exception.executor;

/**
 * @author: mahao
 * @date: 2021/7/16 13:49
 */
public abstract class SqlException extends ExecutorException {
    private static final long serialVersionUID = -9111502309122086145L;

    public SqlException() {
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(Throwable cause) {
        super(cause);
    }

    public SqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
