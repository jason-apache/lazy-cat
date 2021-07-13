package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author: mahao
 * @date: 2021/7/13 12:18
 */
public abstract class AbstractEntrustController implements EntrustController {

    protected final UrlPathHelper urlPathHelper = UrlPathHelper.defaultInstance;
    protected final ApiEntryInfoProvider apiEntryInfoProvider;
    protected final ExecutorHolder executorHolder;
    protected final String apiPath;
    private final int symbolLength = String.valueOf(ApiConstant.PATH_SYMBOL).length() + String.valueOf(ApiConstant.PATH_SYMBOL).length();

    public AbstractEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ExecutorHolder executorHolder, String apiPath) {
        this.apiEntryInfoProvider = apiEntryInfoProvider;
        this.executorHolder = executorHolder;
        this.apiPath = apiPath;
    }
}
