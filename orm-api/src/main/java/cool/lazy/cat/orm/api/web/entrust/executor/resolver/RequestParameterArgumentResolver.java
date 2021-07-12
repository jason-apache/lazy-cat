package cool.lazy.cat.orm.api.web.entrust.executor.resolver;

import cool.lazy.cat.orm.api.exception.BadRequestException;
import cool.lazy.cat.orm.api.web.entrust.executor.annotation.Param;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/8 13:07
 */
public class RequestParameterArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean support(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        Param param = methodParameter.getParameterAnnotation(Param.class);
        if (null != param) {
            String val = request.getParameter(param.name());
            if (null == val) {
                if (param.required()) {
                    throw new BadRequestException("必要的请求参数！: " + param.name());
                }
                if (!param.defaultValue().isEmpty()) {
                    return param.defaultValue();
                }
                return null;
            }
            return val;
        }
        return request.getParameter(methodParameter.getParameterName());
    }
}
