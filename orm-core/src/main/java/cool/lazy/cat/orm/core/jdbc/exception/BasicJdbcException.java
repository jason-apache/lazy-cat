package cool.lazy.cat.orm.core.jdbc.exception;


import cool.lazy.cat.orm.core.base.exception.FullyAutomaticMappingException;

/**
 * @author: mahao
 * @date: 2021/3/10 17:10
 * jdbc模块异常基类
 */
public abstract class BasicJdbcException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = 988056426660702101L;

    public BasicJdbcException() {
    }

    public BasicJdbcException(String message) {
        super(message);
    }

    public BasicJdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicJdbcException(Throwable cause) {
        super(cause);
    }

    public BasicJdbcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
