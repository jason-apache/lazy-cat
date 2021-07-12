package cool.lazy.cat.orm.api.web.entrust.executor.resolver;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.exception.ArgumentResolverException;
import cool.lazy.cat.orm.api.exception.BadRequestException;
import cool.lazy.cat.orm.api.web.entrust.executor.annotation.RequestBodyJsonObj;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/7 16:23
 */
public class JsonArgumentResolver implements MethodArgumentResolver {

    protected final ObjectMapper objectMapper;

    public JsonArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean support(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RequestBodyJsonObj.class) != null;
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter parameter) {
        if (!request.getContentType().contains("application/json")) {
            throw new BadRequestException("无法解析json数据: " + request.getContentType());
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()){
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
        } catch (IOException ioException) {
            throw new ArgumentResolverException("参数读取IO异常", ioException.getCause());
        }
        Class<?> pojoType = FullAutoMappingContext.getPojoType();
        Object param;
        try {
            if (null != pojoType) {
                // 转换为pojo类型
                if (List.class.isAssignableFrom(parameter.getParameterType())) {
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, pojoType);
                    param = objectMapper.readValue(sb.toString(), javaType);
                } else {
                    param = objectMapper.readValue(sb.toString(), parameter.getParameterType());
                }
            } else {
                param = objectMapper.readValue(sb.toString(), parameter.getParameterType());
            }
            return param;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("参数解析异常！", e);
        }
    }
}
