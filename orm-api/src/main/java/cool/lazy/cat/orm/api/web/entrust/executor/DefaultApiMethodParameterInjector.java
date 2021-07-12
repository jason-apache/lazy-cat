package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.entrust.executor.resolver.MethodArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/8 12:43
 */
public class DefaultApiMethodParameterInjector implements ApiMethodParameterInjector {

    protected final List<MethodArgumentResolver> argumentResolverList;

    public DefaultApiMethodParameterInjector(List<MethodArgumentResolver> argumentResolverList) {
        this.argumentResolverList = argumentResolverList;
    }

    @Override
    public Object[] inject(HttpServletRequest request, HttpServletResponse response, MethodInfo methodInfo) {
        int length = methodInfo.getParameters().length;
        Object[] args = new Object[length];
        for (int i = 0; i < length; i++) {
            Object arg = null;
            MethodParameter methodParameter = methodInfo.getParameters()[i];
            for (MethodArgumentResolver argumentResolver : argumentResolverList) {
                if (argumentResolver.support(request, response, methodParameter)) {
                    arg = argumentResolver.resolve(request, response, methodParameter);
                    break;
                }
            }
            args[i] = arg;
        }
        return args;
    }
}
