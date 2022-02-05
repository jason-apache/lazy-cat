package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 10:50
 * api方法载体
 */
public interface ApiMethodEntry {

    /**
     * 获取api方法所属bean
     * @return api owner
     */
    EntrustApi getApiBean();

    /**
     * 获取api方法对应的java method
     * @return java method
     */
    MethodInfo getBusinessMethod();

    /**
     * 生成api方法所需的参数
     * @param request 本次请求
     * @param response 响应体
     * @return 方法入参
     */
    Object[] buildParameters(HttpServletRequest request, HttpServletResponse response);
}
