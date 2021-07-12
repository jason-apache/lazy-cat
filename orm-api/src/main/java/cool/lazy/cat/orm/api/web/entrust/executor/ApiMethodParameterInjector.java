package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/8 11:08
 */
public interface ApiMethodParameterInjector {

    Object[] inject(HttpServletRequest request, HttpServletResponse response, MethodInfo methodInfo);
}
