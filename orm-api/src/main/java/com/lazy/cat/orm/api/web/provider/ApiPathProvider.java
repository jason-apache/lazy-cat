package com.lazy.cat.orm.api.web.provider;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: mahao
 * @date: 2021/3/5 20:58
 */
public interface ApiPathProvider {

    /**
     * 根据请求参数查找匹配的api
     * @param request 请求体
     * @return api访问路径
     */
    String getApiPath(HttpServletRequest request);
}
