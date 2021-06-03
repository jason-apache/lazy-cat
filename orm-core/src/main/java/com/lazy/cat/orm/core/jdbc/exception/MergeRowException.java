package com.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/14 11:13
 */
public class MergeRowException extends BasicJdbcException {
    private static final long serialVersionUID = 568309658026839346L;

    public MergeRowException() {
    }

    public MergeRowException(String message) {
        super(message);
    }

    public MergeRowException(String message, Throwable cause) {
        super(message, cause);
    }

    public MergeRowException(Throwable cause) {
        super(cause);
    }

    public MergeRowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
