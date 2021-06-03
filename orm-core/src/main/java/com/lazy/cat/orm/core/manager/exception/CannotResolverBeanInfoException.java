package com.lazy.cat.orm.core.manager.exception;

/**
 * @author: mahao
 * @date: 2021/3/10 10:59
 */
public class CannotResolverBeanInfoException extends BasicManagerException {

    private static final long serialVersionUID = 7314453713152089115L;

    public CannotResolverBeanInfoException() {
    }

    public CannotResolverBeanInfoException(String message) {
        super(message);
    }

    public CannotResolverBeanInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotResolverBeanInfoException(Throwable cause) {
        super(cause);
    }

    public CannotResolverBeanInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
