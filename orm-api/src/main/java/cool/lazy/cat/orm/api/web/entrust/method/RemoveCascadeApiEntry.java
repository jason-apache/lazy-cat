package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class RemoveCascadeApiEntry extends AbstractApiMethodEntry implements ApiMethodEntry {

    public RemoveCascadeApiEntry(EntrustApi api) {
        super(api, "removeCascade", List.class);
    }

}
