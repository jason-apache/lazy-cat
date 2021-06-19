package cool.lazy.cat.orm.api.web.entrust.handle;


import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.ErrorData;
import cool.lazy.cat.orm.api.web.entrust.ErrorMsg;

/**
 * @author: mahao
 * @date: 2021/4/24 21:10
 */
public class BasicExceptionHandler implements ExceptionHandler {

    @Override
    public boolean canSolve(Throwable e) {
        return e != null;
    }

    @Override
    public ErrorData handle(Throwable e) {
        return new ErrorData(ErrorMsg.ERROR.getStatus(), ErrorMsg.ERROR.getCode(), ErrorMsg.ERROR.getMsg());
    }

    @Override
    public int order() {
        return ApiConstant.DEFAULT_ORDER + 1000;
    }
}
