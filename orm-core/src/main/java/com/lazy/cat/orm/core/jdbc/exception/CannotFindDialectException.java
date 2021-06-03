package com.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/25 20:01
 */
public class CannotFindDialectException extends BasicJdbcException {
    private static final long serialVersionUID = 5928515868135324668L;

    public CannotFindDialectException() {
    }

    public CannotFindDialectException(String message) {
        super(message);
    }

    public CannotFindDialectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFindDialectException(Throwable cause) {
        super(cause);
    }

    public CannotFindDialectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
