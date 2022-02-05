package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.FullAutoMappingContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class SaveApiEntry extends AbstractJacksonParameterApiMethodEntry implements ApiMethodEntry {

    public SaveApiEntry(EntrustApi api, ObjectMapper objectMapper) {
        super(api, objectMapper, "save", List.class);
    }

    @Override
    public Object[] buildParameters(HttpServletRequest request, HttpServletResponse response) {
        return new Object[]{super.readJsonObj(request, objectMapper.getTypeFactory().constructParametricType(List.class, FullAutoMappingContext.getPojoType()))};
    }
}
