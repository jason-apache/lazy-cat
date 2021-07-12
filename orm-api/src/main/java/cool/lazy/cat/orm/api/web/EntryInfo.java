package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import org.springframework.http.HttpMethod;

/**
 * @author: mahao
 * @date: 2021/7/7 15:22
 */
public class EntryInfo {

    private final ApiPojoSubject apiPojoSubject;
    private final String path;
    private final Class<? extends ApiMethodEntry> api;
    private final HttpMethod method;

    public EntryInfo(ApiPojoSubject apiPojoSubject, String path, Class<? extends ApiMethodEntry> api, HttpMethod method) {
        this.apiPojoSubject = apiPojoSubject;
        this.path = path;
        this.api = api;
        this.method = method;
    }

    public ApiPojoSubject getApiPojoSubject() {
        return apiPojoSubject;
    }

    public String getPath() {
        return path;
    }

    public Class<? extends ApiMethodEntry> getApi() {
        return api;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
