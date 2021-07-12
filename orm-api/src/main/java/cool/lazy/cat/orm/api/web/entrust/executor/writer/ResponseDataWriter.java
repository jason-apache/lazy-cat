package cool.lazy.cat.orm.api.web.entrust.executor.writer;

import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/8 18:14
 */
public interface ResponseDataWriter {

    boolean support(MethodInfo methodInfo);

    void doResponse(Object data, HttpServletResponse response, ApiMethodEntry methodEntry);
}
