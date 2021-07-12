package cool.lazy.cat.orm.api.web.entrust.filter;

import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: mahao
 * @date: 2021/7/5 17:29
 */
public class EntrustFilter extends OncePerRequestFilter {

    protected final ApiEntryInfoProvider apiEntryInfoProvider;
    protected final ExecutorHolder executorHolder;
    protected final String entrustPathNode;
    protected final int entrustPathNodeLen;

    public EntrustFilter(ApiEntryInfoProvider apiEntryInfoProvider, ExecutorHolder executorHolder, String entrustPathNode) {
        this.apiEntryInfoProvider = apiEntryInfoProvider;
        this.executorHolder = executorHolder;
        this.entrustPathNode = entrustPathNode;
        this.entrustPathNodeLen = (ApiConstant.PATH_SYMBOL + entrustPathNode + ApiConstant.PATH_SYMBOL).length();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取匹配的代理pojo
        EntryInfo entryInfo = apiEntryInfoProvider.provider(this.cut(request.getRequestURI()), HttpMethod.resolve(request.getMethod()));
        if (null != entryInfo) {
            try {
                FullAutoMappingContext.setPojoType(entryInfo.getApiPojoSubject().getPojoType());
                executorHolder.execute(request, response, entryInfo);
            } finally {
                FullAutoMappingContext.removePojoType();
            }
        } else {
            // 找不到代理则放行
            filterChain.doFilter(request, response);
        }
    }

    private String cut(String uri) {
        if (uri.length() > entrustPathNodeLen) {
            return uri.substring(entrustPathNodeLen -1);
        }
        return uri;
    }
}
