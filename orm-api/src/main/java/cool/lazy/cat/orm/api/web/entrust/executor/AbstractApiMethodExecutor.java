package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.exception.ReflectInvocationException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ResponseDataWriterHolder;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.method.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/7/6 18:00
 */
public abstract class AbstractApiMethodExecutor implements ApiMethodExecutor {

    protected final ApiMethodParameterInjector apiMethodParameterInjector;
    protected final Map<Class<? extends ApiMethodEntry>, ApiMethodEntry> apiMethodEntryList;
    protected final ResponseDataWriterHolder responseDataWriterHolder;

    protected AbstractApiMethodExecutor(ApiMethodParameterInjector apiMethodParameterInjector, ResponseDataWriterHolder responseDataWriterHolder, List<ApiMethodEntry> apiMethodEntryList) {
        this.apiMethodParameterInjector = apiMethodParameterInjector;
        this.responseDataWriterHolder = responseDataWriterHolder;
        this.apiMethodEntryList = apiMethodEntryList.stream().collect(Collectors.toMap(ApiMethodEntry::getClass, Function.identity()));
    }

    protected Object call(HttpServletRequest request, HttpServletResponse response, ApiMethodEntry methodEntry) {
        EntrustApi apiBean = methodEntry.getApiBean();
        MethodInfo businessMethod = methodEntry.getBusinessMethod();
        Object[] args = apiMethodParameterInjector.inject(request, response, businessMethod);
        try {
            return businessMethod.getMethod().invoke(apiBean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectInvocationException("反射执行代理api方法异常", e);
        }
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo) {
        ApiMethodEntry methodEntry = apiMethodEntryList.get(entryInfo.getApi());
        Object result = this.call(request, response, methodEntry);
        responseDataWriterHolder.doResponse(result, response, methodEntry);
    }
}
