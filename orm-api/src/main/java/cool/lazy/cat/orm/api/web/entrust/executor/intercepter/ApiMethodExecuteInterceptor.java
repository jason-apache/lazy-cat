package cool.lazy.cat.orm.api.web.entrust.executor.intercepter;

import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2022-01-28 16:47
 */
public interface ApiMethodExecuteInterceptor {

    /**
     * api方法执行之前的操作
     * @param request 本次请求
     * @param response 响应体
     * @param entryInfo api信息
     * @param methodEntry api方法入口相关参数
     */
    void beforeExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry);

    /**
     * api方法待执行时的操作
     * @param request 本次请求
     * @param response 响应体
     * @param entryInfo api信息
     * @param methodEntry api方法入口相关参数
     * @param args 构建的请求参数
     */
    void pendingExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args);

    /**
     * api方法执行之后的操作
     * @param request 本次请求
     * @param response 响应体
     * @param entryInfo api信息
     * @param methodEntry api方法入口相关参数
     * @param args 构建的请求参数
     * @param executeResult 执行apiMethod后的返回值
     */
    void afterExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args, Object executeResult);
}
