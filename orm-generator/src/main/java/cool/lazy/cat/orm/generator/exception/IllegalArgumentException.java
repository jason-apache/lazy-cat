package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/15 12:06
 */
public class IllegalArgumentException extends BasicGeneratorException {
    private static final long serialVersionUID = 3771395574341454448L;

    public IllegalArgumentException() {
    }

    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
