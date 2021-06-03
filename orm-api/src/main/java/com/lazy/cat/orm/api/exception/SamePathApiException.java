package com.lazy.cat.orm.api.exception;


import com.lazy.cat.orm.core.manager.exception.BasicManagerException;

/**
 * @author: mahao
 * @date: 2021/3/5 19:31
 */
public class SamePathApiException extends BasicManagerException {
    private static final long serialVersionUID = -544771789303016563L;

    public SamePathApiException() {
    }

    public SamePathApiException(String message) {
        super(message);
    }

    public SamePathApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SamePathApiException(Throwable cause) {
        super(cause);
    }

    public SamePathApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
