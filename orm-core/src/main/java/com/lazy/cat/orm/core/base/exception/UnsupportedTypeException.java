package com.lazy.cat.orm.core.base.exception;


import com.lazy.cat.orm.core.jdbc.exception.BasicJdbcException;

/**
 * @author: mahao
 * @date: 2021/3/14 11:50
 */
public class UnsupportedTypeException extends BasicJdbcException {
    private static final long serialVersionUID = 767035954109363873L;

    public UnsupportedTypeException() {
    }

    public UnsupportedTypeException(String message) {
        super(message);
    }

    public UnsupportedTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedTypeException(Throwable cause) {
        super(cause);
    }

    public UnsupportedTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
