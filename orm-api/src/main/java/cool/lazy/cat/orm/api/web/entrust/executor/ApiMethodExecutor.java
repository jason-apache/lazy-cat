package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.EntryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/6 17:47
 * api方法执行器
 */
public interface ApiMethodExecutor {

    /**
     * 根据api入口 执行指定api
     * @param request 本次请求
     * @param response 响应体
     * @param entryInfo api
     * @return api返回值
     */
    Object execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo);

}
