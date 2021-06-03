package com.lazy.cat.orm.api.exception;

import com.lazy.cat.orm.core.manager.exception.BasicManagerException;

/**
 * @author: mahao
 * @date: 2021/3/5 19:09
 */
public class ExistPathApiException extends BasicManagerException {
    private static final long serialVersionUID = 5439358308080559549L;

    public ExistPathApiException() {
    }

    public ExistPathApiException(String message) {
        super(message);
    }

    public ExistPathApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistPathApiException(Throwable cause) {
        super(cause);
    }

    public ExistPathApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
