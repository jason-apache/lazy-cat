package com.lazy.cat.orm.core.base.exception;

/**
 * @author: mahao
 * @date: 2021/3/12 19:21
 */
public class FieldAlreadyExistsException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = -7653852343992163758L;

    public FieldAlreadyExistsException() {
    }

    public FieldAlreadyExistsException(String message) {
        super(message);
    }

    public FieldAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public FieldAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
