package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:38
 */
public abstract class BasicGeneratorException extends RuntimeException {
    private static final long serialVersionUID = -678886354605914844L;

    public BasicGeneratorException() {
    }

    public BasicGeneratorException(String message) {
        super(message);
    }

    public BasicGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicGeneratorException(Throwable cause) {
        super(cause);
    }

    public BasicGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
