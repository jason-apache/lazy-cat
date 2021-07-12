package cool.lazy.cat.orm.api.web.entrust.method;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/7/8 11:09
 */
public class MethodInfo {

    private final Method method;
    private final MethodParameter[] parameters;

    public MethodInfo(Method method, MethodParameter[] parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    public Method getMethod() {
        return method;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }
}
