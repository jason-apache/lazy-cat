package com.lazy.cat.orm.core.jdbc.exception;

/**
 * @author: mahao
 * @date: 2021/3/10 14:35
 */
public class RowMappingException extends BasicJdbcException {
    private static final long serialVersionUID = 5249662680494879669L;

    public RowMappingException() {
    }

    public RowMappingException(String message) {
        super(message);
    }

    public RowMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RowMappingException(Throwable cause) {
        super(cause);
    }

    public RowMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
