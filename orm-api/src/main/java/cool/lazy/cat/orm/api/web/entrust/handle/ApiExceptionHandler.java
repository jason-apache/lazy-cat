package cool.lazy.cat.orm.api.web.entrust.handle;


import cool.lazy.cat.orm.api.exception.ApiException;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.ErrorData;
import cool.lazy.cat.orm.api.web.entrust.ErrorMsg;

/**
 * @author: mahao
 * @date: 2021/4/25 18:00
 */
public class ApiExceptionHandler implements ExceptionHandler {
    @Override
    public boolean canSolve(Throwable e) {
        return e instanceof ApiException;
    }

    @Override
    public ErrorData handle(Throwable e) {
        return new ErrorData(ErrorMsg.SERVER_ERROR.getStatus(), ErrorMsg.SERVER_ERROR.getCode(), e.getCause().getMessage());
    }

    @Override
    public int order() {
        return ApiConstant.DEFAULT_ORDER + 10;
    }
}
