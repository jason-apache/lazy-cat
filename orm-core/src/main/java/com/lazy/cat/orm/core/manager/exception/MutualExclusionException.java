package com.lazy.cat.orm.core.manager.exception;

/**
 * @author: mahao
 * @date: 2021/3/12 13:02
 */
public class MutualExclusionException extends BasicManagerException {
    private static final long serialVersionUID = -7760116035238728498L;

    public MutualExclusionException() {
    }

    public MutualExclusionException(String message) {
        super(message);
    }

    public MutualExclusionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MutualExclusionException(Throwable cause) {
        super(cause);
    }

    public MutualExclusionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
