package cool.lazy.cat.orm.api.web.entrust.executor.holder;

import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 16:05
 */
public abstract class AbstractExecutorHolder implements ExecutorHolder {

    protected final ApiMethodExecutor apiMethodExecutor;

    protected AbstractExecutorHolder(ApiMethodExecutor apiMethodExecutor) {
        this.apiMethodExecutor = apiMethodExecutor;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo) {
        this.preExecute(request, response, entryInfo);
        this.apiMethodExecutor.execute(request, response, entryInfo);
    }

    protected abstract void preExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo);
}
