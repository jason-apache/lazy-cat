package com.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/4/11 16:52
 */
public class EmptyColumnException extends BasicJdbcException {
    private static final long serialVersionUID = 6555584524890967137L;

    public EmptyColumnException() {
    }

    public EmptyColumnException(String message) {
        super(message);
    }

    public EmptyColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyColumnException(Throwable cause) {
        super(cause);
    }

    public EmptyColumnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
