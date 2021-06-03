package com.lazy.cat.orm.api.exception;


import com.lazy.cat.orm.core.manager.exception.BasicManagerException;

/**
 * @author: mahao
 * @date: 2021/3/5 19:08
 */
public class ExistNameSpaceException extends BasicManagerException {
    private static final long serialVersionUID = -2731081360830752507L;

    public ExistNameSpaceException() {
    }

    public ExistNameSpaceException(String message) {
        super(message);
    }

    public ExistNameSpaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistNameSpaceException(Throwable cause) {
        super(cause);
    }

    public ExistNameSpaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
