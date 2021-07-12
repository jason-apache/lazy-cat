package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.exception.CannotLoadApiMethodException;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/7/8 11:10
 */
public abstract class AbstractApiMethodEntry implements ApiMethodEntry {

    protected final EntrustApi api;
    protected final MethodInfo methodInfo;

    public AbstractApiMethodEntry(EntrustApi api, String methodName, Class<?>... parameterTypes) {
        this.api = api;
        Method method = this.loadMethod(methodName, parameterTypes);
        this.methodInfo = build(method);
    }

    protected Method loadMethod(String methodName, Class<?>... parameterTypes) {
        try {
            Method method = this.api.getClass().getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new CannotLoadApiMethodException("无法加载方法: " + methodName, e);
        }
    }

    protected MethodInfo build(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        MethodParameter[] methodParameters = new MethodParameter[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            methodParameters[i] = new MethodParameter(method, i);
        }
        return new MethodInfo(method, methodParameters);
    }

    @Override
    public EntrustApi getApiBean() {
        return this.api;
    }

    @Override
    public MethodInfo getBusinessMethod() {
        return this.methodInfo;
    }
}
