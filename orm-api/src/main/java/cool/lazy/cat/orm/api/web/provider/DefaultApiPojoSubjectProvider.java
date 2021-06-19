package cool.lazy.cat.orm.api.web.provider;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.web.UriPojoMapping;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: mahao
 * @date: 2021/3/6 09:53
 */
public class DefaultApiPojoSubjectProvider implements ApiPojoSubjectProvider {

    @Autowired
    protected ApiPojoManager apiPojoManager;
    @Autowired
    protected UriPojoMapping uriPojoMapping;

    @Override
    public ApiPojoSubject getPojoSubject(HttpServletRequest request) {
        String uri = new UrlPathHelper().getOriginatingRequestUri(request);
        return this.getByUriAndType(uri, request.getMethod());
    }

    private ApiPojoSubject getByUriAndType(String uri, String type) {
        if (StringUtil.isBlank(uri)) {
            return null;
        }
        return uriPojoMapping.getByUriAndType(uri, type);
    }
}
