package cool.lazy.cat.orm.core.base.exception;

/**
 * @author: mahao
 * @date: 2021/3/4 20:47
 * 异常基类
 */
public abstract class FullyAutomaticMappingException extends RuntimeException {
    private static final long serialVersionUID = 6695979439710166034L;

    public FullyAutomaticMappingException() {
    }

    public FullyAutomaticMappingException(String message) {
        super(message);
    }

    public FullyAutomaticMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FullyAutomaticMappingException(Throwable cause) {
        super(cause);
    }

    public FullyAutomaticMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
