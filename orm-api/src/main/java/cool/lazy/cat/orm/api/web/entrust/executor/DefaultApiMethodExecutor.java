package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.entrust.executor.holder.ResponseDataWriterHolder;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/12 13:44
 */
public class DefaultApiMethodExecutor extends AbstractApiMethodExecutor implements ApiMethodExecutor {

    protected DefaultApiMethodExecutor(ApiMethodParameterInjector apiMethodParameterInjector, ResponseDataWriterHolder responseDataWriterHolder, List<ApiMethodEntry> apiMethodEntryList) {
        super(apiMethodParameterInjector, responseDataWriterHolder, apiMethodEntryList);
    }
}
