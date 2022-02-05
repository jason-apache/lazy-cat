package cool.lazy.cat.orm.api.web.entrust;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/7/8 11:09
 */
public class MethodInfoImpl implements MethodInfo {

    private final Method method;
    private final MethodParameter[] parameters;

    public MethodInfoImpl(Method method, MethodParameter[] parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public MethodParameter[] getParameters() {
        return parameters;
    }
}
