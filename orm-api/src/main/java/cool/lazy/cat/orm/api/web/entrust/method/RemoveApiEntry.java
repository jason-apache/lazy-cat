package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class RemoveApiEntry extends AbstractApiMethodEntry implements ApiMethodEntry {

    public RemoveApiEntry(EntrustApi api) {
        super(api, "remove", List.class);
    }

}
