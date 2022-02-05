package cool.lazy.cat.orm.core.jdbc.exception.executor;

import cool.lazy.cat.orm.core.jdbc.exception.BasicJdbcException;

/**
 * @author: mahao
 * @date: 2021/7/16 13:48
 */
public abstract class ExecutorException extends BasicJdbcException {
    private static final long serialVersionUID = -7430334951009806238L;

    public ExecutorException() {
    }

    public ExecutorException(String message) {
        super(message);
    }

    public ExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutorException(Throwable cause) {
        super(cause);
    }

    public ExecutorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
