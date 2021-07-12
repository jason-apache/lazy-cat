package cool.lazy.cat.orm.api.web.entrust.executor.resolver;

import org.springframework.core.MethodParameter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/8 13:04
 */
public class ServletRequestArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean support(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return ServletRequest.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return request;
    }
}
