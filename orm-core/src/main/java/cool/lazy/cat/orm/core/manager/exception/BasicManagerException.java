package cool.lazy.cat.orm.core.manager.exception;


import cool.lazy.cat.orm.core.base.exception.FullyAutomaticMappingException;

/**
 * @author: mahao
 * @date: 2021/3/10 12:39
 * manager异常基类
 */
public abstract class BasicManagerException extends FullyAutomaticMappingException {

    private static final long serialVersionUID = 4336593269350194565L;

    public BasicManagerException() {
    }

    public BasicManagerException(String message) {
        super(message);
    }

    public BasicManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicManagerException(Throwable cause) {
        super(cause);
    }

    public BasicManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
