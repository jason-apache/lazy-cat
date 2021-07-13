package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.exception.ReflectInvocationException;
import cool.lazy.cat.orm.api.exception.UnKnowTargetApiMethodEntryException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
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

    protected final Map<Class<? extends ApiMethodEntry>, ApiMethodEntry> apiMethodEntryMap;

    protected AbstractApiMethodExecutor(List<ApiMethodEntry> apiMethodEntryList) {
        this.apiMethodEntryMap = apiMethodEntryList.stream().collect(Collectors.toMap(ApiMethodEntry::getClass, Function.identity()));
    }

    protected Object call(HttpServletRequest request, HttpServletResponse response, ApiMethodEntry methodEntry) {
        EntrustApi apiBean = methodEntry.getApiBean();
        MethodInfo businessMethod = methodEntry.getBusinessMethod();
        Object[] args = methodEntry.buildParameters(request, response);
        try {
            return businessMethod.getMethod().invoke(apiBean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectInvocationException("反射执行代理api方法异常", e);
        }
    }

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo) {
        ApiMethodEntry methodEntry = apiMethodEntryMap.get(entryInfo.getApi());
        if (null == methodEntry) {
            throw new UnKnowTargetApiMethodEntryException("无法获取api方法, 请检查该bean是否在IOC容器中 " + entryInfo.getApiPojoSubject().getPojoType().getName()
                + "#" + entryInfo.getApi().getName());
        }
        return this.call(request, response, methodEntry);
    }
}
