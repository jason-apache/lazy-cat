package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.ParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021-11-16 16:14
 * api入口
 */
public interface EntryInfo extends ParameterizationInfo {

    /**
     * 当前api对应的pojo
     * @return pojo class
     */
    Class<?> getPojoType();

    /**
     * 获取pojo nameSpace
     * @return nameSpace
     */
    String getNameSpace();

    /**
     * 完整路径 nameSpace + path
     * @return api 完整路径
     */
    String getFullPath();

    /**
     * 获取api方法所属bean
     * @return api owner
     */
    Class<? extends ApiMethodEntry> getApi();

    /**
     * 支持的请求方式
     * @return method
     */
    HttpMethod[] getMethods();
}
