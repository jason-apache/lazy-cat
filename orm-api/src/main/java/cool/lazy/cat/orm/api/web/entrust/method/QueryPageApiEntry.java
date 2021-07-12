package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class QueryPageApiEntry extends AbstractApiMethodEntry implements ApiMethodEntry {

    public QueryPageApiEntry(EntrustApi api) {
        super(api, "queryPage", QueryInfo.class);
    }

}
