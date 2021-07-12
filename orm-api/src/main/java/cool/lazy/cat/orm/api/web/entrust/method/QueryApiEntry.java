package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class QueryApiEntry extends AbstractApiMethodEntry implements ApiMethodEntry {

    public QueryApiEntry(EntrustApi api) {
        super(api, "query", QueryInfo.class, HttpServletRequest.class, HttpServletResponse.class, String.class);
    }

}
