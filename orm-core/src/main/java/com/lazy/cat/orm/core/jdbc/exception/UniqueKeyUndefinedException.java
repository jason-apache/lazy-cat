package com.lazy.cat.orm.core.jdbc.exception;


import com.lazy.cat.orm.core.base.exception.FullyAutomaticMappingException;

/**
 * @author: mahao
 * @date: 2021/3/12 19:26
 */
public class UniqueKeyUndefinedException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = 8950664962681836645L;

    public UniqueKeyUndefinedException() {
    }

    public UniqueKeyUndefinedException(String message) {
        super(message);
    }

    public UniqueKeyUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueKeyUndefinedException(Throwable cause) {
        super(cause);
    }

    public UniqueKeyUndefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
