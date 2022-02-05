package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.exception.JsonReadException;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: mahao
 * @date: 2021-12-07 14:05
 * 使用jackson作为json参数解析
 */
public abstract class AbstractJacksonParameterApiMethodEntry extends AbstractApiMethodEntry {

    protected final ObjectMapper objectMapper;

    public AbstractJacksonParameterApiMethodEntry(EntrustApi api, ObjectMapper objectMapper, String methodName, Class<?>... parameterTypes) {
        super(api, methodName, parameterTypes);
        this.objectMapper = objectMapper;
    }

    protected Object readJsonObj(HttpServletRequest request, JavaType javaType) {
        try {
            return this.objectMapper.readValue(request.getInputStream(), javaType);
        } catch (IOException e) {
            throw new JsonReadException("json解析异常", e);
        }
    }
}
