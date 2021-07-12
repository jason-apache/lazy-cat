package cool.lazy.cat.orm.api.web.entrust.executor.holder;

import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/12 13:14
 */
public interface ResponseDataWriterHolder {

    void doResponse(Object data, HttpServletResponse response, ApiMethodEntry methodEntry);
}
