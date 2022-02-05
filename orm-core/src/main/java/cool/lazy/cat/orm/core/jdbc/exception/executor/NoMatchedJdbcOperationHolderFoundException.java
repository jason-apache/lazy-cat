package cool.lazy.cat.orm.core.jdbc.exception.executor;

import cool.lazy.cat.orm.core.jdbc.exception.BasicJdbcException;

/**
 * @author: mahao
 * @date: 2021/7/22 11:41
 */
public class NoMatchedJdbcOperationHolderFoundException extends BasicJdbcException {
    private static final long serialVersionUID = 5422012123241521018L;

    public NoMatchedJdbcOperationHolderFoundException() {
    }

    public NoMatchedJdbcOperationHolderFoundException(String message) {
        super(message);
    }

    public NoMatchedJdbcOperationHolderFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMatchedJdbcOperationHolderFoundException(Throwable cause) {
        super(cause);
    }

    public NoMatchedJdbcOperationHolderFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
