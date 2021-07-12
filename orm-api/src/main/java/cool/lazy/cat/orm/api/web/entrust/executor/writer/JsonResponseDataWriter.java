package cool.lazy.cat.orm.api.web.entrust.executor.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.exception.ApiMethodResponseException;
import cool.lazy.cat.orm.api.exception.JsonFormatException;
import cool.lazy.cat.orm.api.web.entrust.executor.annotation.JsonWriter;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @author: mahao
 * @date: 2021/7/8 18:25
 */
public class JsonResponseDataWriter implements ResponseDataWriter{

    protected final ObjectMapper objectMapper;

    public JsonResponseDataWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean support(MethodInfo methodInfo) {
        return methodInfo.getMethod().getAnnotation(JsonWriter.class) != null;
    }

    @Override
    public void doResponse(Object data, HttpServletResponse response, ApiMethodEntry methodEntry) {
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new JsonFormatException("执行数据响应至客户端异常", e);
        }
        try (BufferedWriter writer = new BufferedWriter(response.getWriter())){
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            throw new ApiMethodResponseException("执行数据响应至客户端异常", e);
        }
    }
}
