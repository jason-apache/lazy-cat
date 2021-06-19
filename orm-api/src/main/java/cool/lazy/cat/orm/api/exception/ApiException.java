package cool.lazy.cat.orm.api.exception;


import cool.lazy.cat.orm.core.base.exception.FullyAutomaticMappingException;

/**
 * @author: mahao
 * @date: 2021/3/14 12:54
 * Api异常基类
 */
public abstract class ApiException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = -5680537344076056249L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
