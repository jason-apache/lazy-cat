package cool.lazy.cat.orm.core.base.exception;

/**
 * @author: mahao
 * @date: 2021/3/30 20:15
 */
public class InitFailedException extends FullyAutomaticMappingException {
    private static final long serialVersionUID = -341443846839158574L;

    public InitFailedException() {
    }

    public InitFailedException(String message) {
        super(message);
    }

    public InitFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitFailedException(Throwable cause) {
        super(cause);
    }

    public InitFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
