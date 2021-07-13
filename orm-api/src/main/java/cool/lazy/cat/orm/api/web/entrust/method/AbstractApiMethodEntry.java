package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.exception.CannotLoadApiMethodException;
import cool.lazy.cat.orm.api.exception.JsonReadException;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/7/8 11:10
 */
public abstract class AbstractApiMethodEntry implements ApiMethodEntry {

    protected final EntrustApi api;
    protected final MethodInfo methodInfo;
    protected final ObjectMapper objectMapper;

    public AbstractApiMethodEntry(EntrustApi api, ObjectMapper objectMapper, String methodName, Class<?>... parameterTypes) {
        this.api = api;
        this.objectMapper = objectMapper;
        Method method = this.loadMethod(methodName, parameterTypes);
        this.methodInfo = this.buildMethodInfo(method);
    }

    protected Method loadMethod(String methodName, Class<?>... parameterTypes) {
        try {
            Method method = this.api.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new CannotLoadApiMethodException("无法加载方法: " + this.api.getClass().getName()+ "#" + methodName, e);
        }
    }

    protected MethodInfo buildMethodInfo(Method method) {
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

    protected Object readJsonObj(HttpServletRequest request, JavaType javaType) {
        try {
            return this.objectMapper.readValue(request.getInputStream(), javaType);
        } catch (IOException e) {
            throw new JsonReadException("json解析异常", e);
        }
    }
}
