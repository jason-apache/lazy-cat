package cool.lazy.cat.orm.api.web.provider;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/5 10:11
 */
public class DefaultApiPathProvider implements ApiPathProvider {

    @Autowired
    protected ApiPojoManager apiPojoManager;
    @Autowired
    protected ApiPojoSubjectProvider apiPojoSubjectProvider;

    @Override
    public String getApiPath(HttpServletRequest request) {
        String uri = new UrlPathHelper().getOriginatingRequestUri(request);
        if (StringUtil.isEmpty(uri)) {
            return ApiConstant.UN_KNOW;
        }
        ApiPojoSubject subject = apiPojoSubjectProvider.getPojoSubject(request);
        if (subject == null) {
            return ApiConstant.UN_KNOW;
        }
        return this.getByUri(uri, subject.getPojoType());
    }

    private String getByUri(String uri, Class<?> pojoType) {
        ApiPojoSubject subject = apiPojoManager.getByPojoType(pojoType);
        List<ApiPojoSubject.EntryMethod> entryMethodList = subject.getEntryMethodList();
        for (ApiPojoSubject.EntryMethod method : entryMethodList) {
            if (uri.equals(subject.getNameSpace() + method.getPath())) {
                return method.getMappingApi();
            }
        }
        return ApiConstant.UN_KNOW;
    }
}
