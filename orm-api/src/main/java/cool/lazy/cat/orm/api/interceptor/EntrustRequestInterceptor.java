package cool.lazy.cat.orm.api.interceptor;

import cool.lazy.cat.orm.api.web.provider.ApiPojoSubjectProvider;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/3/7 16:16
 * 负责设置上下文中的pojo类型的拦截器
 */
public class EntrustRequestInterceptor implements HandlerInterceptor {

    protected ApiPojoSubjectProvider apiPojoSubjectProvider;

    public EntrustRequestInterceptor(ApiPojoSubjectProvider apiPojoSubjectProvider) {
        this.apiPojoSubjectProvider = apiPojoSubjectProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        FullAutoMappingContext.setPojoType(apiPojoSubjectProvider.getPojoSubject(request).getPojoType());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FullAutoMappingContext.removePojoType();
    }
}
