package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:40
 */
public class GetDatabaseInfoException extends CodeGenerateException {
    private static final long serialVersionUID = 9058061665991528697L;

    public GetDatabaseInfoException(String message) {
        super(message);
    }

    public GetDatabaseInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetDatabaseInfoException(Throwable cause) {
        super(cause);
    }

    public GetDatabaseInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
