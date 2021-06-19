package cool.lazy.cat.orm.api.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.exception.BadRequestException;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.web.annotation.FullyAutomaticArgument;
import cool.lazy.cat.orm.api.web.provider.ApiPojoSubjectProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/7 13:55
 * 对接口数据进行解析、反射
 */
public class FullyAutomaticArgumentResolver implements HandlerMethodArgumentResolver {

    protected final ApiPojoSubjectProvider apiPojoSubjectProvider;
    protected final ObjectMapper objectMapper;

    public FullyAutomaticArgumentResolver(ApiPojoSubjectProvider apiPojoSubjectProvider, ObjectMapper objectMapper) {
        this.apiPojoSubjectProvider = apiPojoSubjectProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 只处理FullyAutomaticArgument注解标注的参数
        return parameter.getParameterAnnotation(FullyAutomaticArgument.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // 只处理json数据
        if (!request.getContentType().contains("application/json")) {
            return null;
        }
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int len;
        while ((len = reader.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }

        // 从上下文中获取当前操作的pojo类型
        ApiPojoSubject apiPojoSubject = apiPojoSubjectProvider.getPojoSubject(request);
        Object param;
        try {
            if (null != apiPojoSubject) {
                // 转换为pojo类型
                if (List.class.isAssignableFrom(parameter.getParameterType())) {
                    JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, apiPojoSubject.getPojoType());
                    param = objectMapper.readValue(sb.toString(), javaType);
                } else {
                    param = objectMapper.readValue(sb.toString(), parameter.getParameterType());
                }
            } else {
                param = objectMapper.readValue(sb.toString(), parameter.getParameterType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("参数异常！");
        }
        return param;
    }
}
