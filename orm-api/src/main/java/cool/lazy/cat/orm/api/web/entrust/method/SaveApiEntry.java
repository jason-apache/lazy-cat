package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:28
 */
public class SaveApiEntry extends AbstractApiMethodEntry implements ApiMethodEntry {

    public SaveApiEntry(EntrustApi api) {
        super(api, "save", List.class);
    }

}
