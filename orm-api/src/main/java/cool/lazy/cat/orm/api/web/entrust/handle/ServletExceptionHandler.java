package cool.lazy.cat.orm.api.web.entrust.handle;

import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.ErrorData;
import cool.lazy.cat.orm.api.web.entrust.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/4/25 16:17
 */
public class ServletExceptionHandler implements ExceptionHandler {

    protected List<ExceptionHandler> exceptionHandlerList;

    @Autowired
    private void initExceptionHandler(List<ExceptionHandler> exceptionHandlerList) {
        exceptionHandlerList.sort(Comparator.comparingInt(ExceptionHandler::order));
        this.exceptionHandlerList = exceptionHandlerList.stream().filter(e -> e.getClass() != this.getClass()
                && !(e instanceof ServletExceptionHandler)).collect(Collectors.toList());
    }

    @Override
    public boolean canSolve(Throwable e) {
        return e instanceof ServletException;
    }

    @Override
    public ErrorData handle(Throwable e) {
        if (e instanceof NestedServletException) {
            for (ExceptionHandler handler : exceptionHandlerList) {
                if (handler.canSolve(((NestedServletException) e).getRootCause())) {
                    return handler.handle(e);
                }
            }
        }
        return new ErrorData(ErrorMsg.SERVER_ERROR.getStatus(), ErrorMsg.SERVER_ERROR.getCode(), ErrorMsg.SERVER_ERROR.getMsg());
    }

    @Override
    public int order() {
        return ApiConstant.DEFAULT_ORDER + 40;
    }
}
