package cool.lazy.cat.orm.api.web.entrust.executor.holder;

import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 16:05
 */
public class DefaultExecutorHolder extends AbstractExecutorHolder implements ExecutorHolder {

    public DefaultExecutorHolder(ApiMethodExecutor apiMethodExecutor) {
        super(apiMethodExecutor);
    }

    @Override
    public void preExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo) {
    }
}
