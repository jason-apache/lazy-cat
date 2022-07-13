package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:39
 */
public abstract class CodeGenerateException extends BasicGeneratorException {
    private static final long serialVersionUID = -5738906823202332557L;

    public CodeGenerateException() {
    }

    public CodeGenerateException(String message) {
        super(message);
    }

    public CodeGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeGenerateException(Throwable cause) {
        super(cause);
    }

    public CodeGenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
