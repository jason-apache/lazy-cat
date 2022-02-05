package cool.lazy.cat.orm.core.jdbc.exception.executor;

/**
 * @author: mahao
 * @date: 2021/7/16 14:15
 */
public abstract class PrinterSqlException extends SqlException {
    private static final long serialVersionUID = 3446275749327355467L;

    public PrinterSqlException() {
    }

    public PrinterSqlException(String message) {
        super(message);
    }

    public PrinterSqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrinterSqlException(Throwable cause) {
        super(cause);
    }

    public PrinterSqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
