package cool.lazy.cat.orm.core.manager.exception;

/**
 * @author: mahao
 * @date: 2021/4/13 13:43
 */
public class UnKnowPojoException extends BasicManagerException {
    private static final long serialVersionUID = -2590437123757241337L;

    public UnKnowPojoException() {
    }

    public UnKnowPojoException(String message) {
        super(message);
    }

    public UnKnowPojoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowPojoException(Throwable cause) {
        super(cause);
    }

    public UnKnowPojoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
