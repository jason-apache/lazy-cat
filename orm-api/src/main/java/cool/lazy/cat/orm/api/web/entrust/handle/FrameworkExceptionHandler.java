package cool.lazy.cat.orm.api.web.entrust.handle;

import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.ErrorData;
import cool.lazy.cat.orm.api.web.entrust.ErrorMsg;
import cool.lazy.cat.orm.core.base.exception.FullyAutomaticMappingException;

/**
 * @author: mahao
 * @date: 2021/4/25 16:52
 */
public class FrameworkExceptionHandler implements ExceptionHandler {

    @Override
    public boolean canSolve(Throwable e) {
        return e instanceof FullyAutomaticMappingException;
    }

    @Override
    public ErrorData handle(Throwable e) {
        return new ErrorData(ErrorMsg.FRAMEWORK_ERROR.getStatus(), ErrorMsg.FRAMEWORK_ERROR.getCode(), e.getCause().getMessage());
    }

    @Override
    public int order() {
        return ApiConstant.DEFAULT_ORDER + 20;
    }
}
