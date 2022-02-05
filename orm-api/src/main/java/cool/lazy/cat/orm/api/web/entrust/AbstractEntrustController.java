package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author: mahao
 * @date: 2021/7/13 12:18
 */
public abstract class AbstractEntrustController implements EntrustController {

    protected final UrlPathHelper urlPathHelper = UrlPathHelper.defaultInstance;
    protected final ApiEntryInfoProvider apiEntryInfoProvider;
    protected final ApiMethodExecutor apiMethodExecutor;
    protected final String apiPath;

    public AbstractEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ApiMethodExecutor apiMethodExecutor, String apiPath) {
        this.apiEntryInfoProvider = apiEntryInfoProvider;
        this.apiMethodExecutor = apiMethodExecutor;
        this.apiPath = apiPath;
    }
}
