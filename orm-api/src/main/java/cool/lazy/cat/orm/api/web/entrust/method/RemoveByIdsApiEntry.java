package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class RemoveByIdsApiEntry extends AbstractJacksonParameterApiMethodEntry implements ApiMethodEntry {

    public RemoveByIdsApiEntry(EntrustApi api, ObjectMapper objectMapper) {
        super(api, objectMapper, "removeByIds", List.class);
    }

    @Override
    public Object[] buildParameters(HttpServletRequest request, HttpServletResponse response) {
        return new Object[]{super.readJsonObj(request, objectMapper.getTypeFactory().constructParametricType(List.class, String.class))};
    }
}
