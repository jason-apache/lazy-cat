package cool.lazy.cat.orm.api.web.entrust.executor.resolver;

import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 16:16
 */
public interface MethodArgumentResolver {

    boolean support(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter);

    Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter);
}
