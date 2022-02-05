package cool.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/11/3 10:32
 */
public class NoVisibleJdbcOperationHolderException extends BasicJdbcException {
    private static final long serialVersionUID = 946487095025999436L;

    public NoVisibleJdbcOperationHolderException() {
    }

    public NoVisibleJdbcOperationHolderException(String message) {
        super(message);
    }

    public NoVisibleJdbcOperationHolderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoVisibleJdbcOperationHolderException(Throwable cause) {
        super(cause);
    }

    public NoVisibleJdbcOperationHolderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
