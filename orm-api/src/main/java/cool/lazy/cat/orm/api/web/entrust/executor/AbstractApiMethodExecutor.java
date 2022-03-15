package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.exception.ReflectInvocationException;
import cool.lazy.cat.orm.api.exception.UnKnowTargetApiMethodEntryException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.MethodInfo;
import cool.lazy.cat.orm.api.web.entrust.executor.intercepter.ApiMethodExecuteInterceptor;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.base.util.CollectionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/7/6 18:00
 */
public abstract class AbstractApiMethodExecutor implements ApiMethodExecutor {

    /**
     * 所有api
     */
    protected final Map<Class<? extends ApiMethodEntry>, ApiMethodEntry> apiMethodEntryMap;
    protected final List<ApiMethodExecuteInterceptor> interceptorList = new ArrayList<>();

    protected AbstractApiMethodExecutor(List<ApiMethodEntry> apiMethodEntryList, List<ApiMethodExecuteInterceptor> interceptorList) {
        this.apiMethodEntryMap = apiMethodEntryList.stream().collect(Collectors.toMap(ApiMethodEntry::getClass, Function.identity()));
        if (CollectionUtil.isNotEmpty(interceptorList)) {
            this.interceptorList.addAll(interceptorList);
        }
    }

    protected Object call(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry) {
        // 执行之前拦截
        this.beforeExecute(request, response, entryInfo, methodEntry);
        EntrustApi apiBean = methodEntry.getApiBean();
        MethodInfo businessMethod = methodEntry.getBusinessMethod();
        Object[] args = methodEntry.buildParameters(request, response);
        // 待执行时拦截
        this.pendingExecute(request, response, entryInfo, methodEntry, args);
        try {
            Object result = businessMethod.getMethod().invoke(apiBean, args);
            // 执行之后拦截
            this.afterExecute(request, response, entryInfo, methodEntry, args, result);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectInvocationException("反射执行代理api方法异常", e);
        }
    }

    protected void beforeExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry) {
        for (ApiMethodExecuteInterceptor interceptor : interceptorList) {
            interceptor.beforeExecute(request, response, entryInfo, methodEntry);
        }
    }

    protected void pendingExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args) {
        for (ApiMethodExecuteInterceptor interceptor : interceptorList) {
            interceptor.pendingExecute(request, response, entryInfo, methodEntry, args);
        }
    }

    protected void afterExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args, Object executeResult) {
        for (ApiMethodExecuteInterceptor interceptor : interceptorList) {
            interceptor.afterExecute(request, response, entryInfo, methodEntry, args, executeResult);
        }
    }

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo) {
        ApiMethodEntry methodEntry = apiMethodEntryMap.get(entryInfo.getApi());
        if (null == methodEntry) {
            throw new UnKnowTargetApiMethodEntryException("无法获取api方法, 请检查该bean是否在IOC容器中 " + entryInfo.getPojoType().getName()
                + "#" + entryInfo.getApi().getName());
        }
        return this.call(request, response, entryInfo, methodEntry);
    }
}
