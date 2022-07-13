package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/12 17:34
 */
public class UnKnowDialectException extends BasicGeneratorException {
    private static final long serialVersionUID = 5534641982107520013L;

    public UnKnowDialectException() {
    }

    public UnKnowDialectException(String message) {
        super(message);
    }

    public UnKnowDialectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowDialectException(Throwable cause) {
        super(cause);
    }

    public UnKnowDialectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
