package cool.lazy.cat.orm.generator.exception;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:40
 */
public class GetResultSetMetaDataException extends CodeGenerateException {

    private static final long serialVersionUID = -2474150765613396794L;

    public GetResultSetMetaDataException(String message) {
        super(message);
    }

    public GetResultSetMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetResultSetMetaDataException(Throwable cause) {
        super(cause);
    }

    public GetResultSetMetaDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
