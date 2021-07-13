package cool.lazy.cat.orm.api.exception;

/**
 * @author: mahao
 * @date: 2021/7/13 12:20
 */
public class UnKnowTargetBeanException extends ApiException {
    private static final long serialVersionUID = -4128234784781631578L;

    public UnKnowTargetBeanException() {
    }

    public UnKnowTargetBeanException(String message) {
        super(message);
    }

    public UnKnowTargetBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowTargetBeanException(Throwable cause) {
        super(cause);
    }

    public UnKnowTargetBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
