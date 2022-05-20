package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/7/7 15:22
 */
public class EntryInfoImpl extends AbstractParameterizationInfo implements EntryInfo {

    private final Class<?> pojoType;
    private final String nameSpace;
    private final String fullPath;
    private final Class<? extends ApiMethodEntry> api;
    private final HttpMethod[] methods;

    public EntryInfoImpl(Class<?> pojoType, String nameSpace, String fullPath, Class<? extends ApiMethodEntry> api,
                         HttpMethod[] methods, Parameter[] parameters) {
        this.pojoType = pojoType;
        this.nameSpace = nameSpace;
        this.fullPath = fullPath;
        this.api = api;
        this.methods = methods;
        super.initParameter(parameters);
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public String getNameSpace() {
        return nameSpace;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public Class<? extends ApiMethodEntry> getApi() {
        return api;
    }

    @Override
    public HttpMethod[] getMethods() {
        return methods;
    }
}
