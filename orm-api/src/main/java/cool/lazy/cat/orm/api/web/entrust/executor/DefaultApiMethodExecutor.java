package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.entrust.executor.intercepter.ApiMethodExecuteInterceptor;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:44
 */
public class DefaultApiMethodExecutor extends AbstractApiMethodExecutor implements ApiMethodExecutor {

    public DefaultApiMethodExecutor(List<ApiMethodEntry> apiMethodEntryList, List<ApiMethodExecuteInterceptor> interceptorList) {
        super(apiMethodEntryList, interceptorList);
    }
}
