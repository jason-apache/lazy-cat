package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.bo.QueryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class QueryPageApiEntry extends AbstractJacksonParameterApiMethodEntry implements ApiMethodEntry {

    public QueryPageApiEntry(EntrustApi api, ObjectMapper objectMapper) {
        super(api, objectMapper, "queryPage", QueryInfo.class);
    }

    @Override
    public Object[] buildParameters(HttpServletRequest request, HttpServletResponse response) {
        return new Object[]{super.readJsonObj(request, objectMapper.getTypeFactory().constructType(QueryInfo.class))};
    }
}
