package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/13 16:32
 */
public class FieldLoadConfigPropertiesException extends BasicGeneratorException {
    private static final long serialVersionUID = 8731283625734213659L;

    public FieldLoadConfigPropertiesException() {
    }

    public FieldLoadConfigPropertiesException(String message) {
        super(message);
    }

    public FieldLoadConfigPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldLoadConfigPropertiesException(Throwable cause) {
        super(cause);
    }

    public FieldLoadConfigPropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
