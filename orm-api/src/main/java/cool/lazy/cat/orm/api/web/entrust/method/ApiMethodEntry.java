package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 10:50
 */
public interface ApiMethodEntry {

    EntrustApi getApiBean();

    MethodInfo getBusinessMethod();

    Object[] buildParameters(HttpServletRequest request, HttpServletResponse response);
}
