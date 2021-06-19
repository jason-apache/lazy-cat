package cool.lazy.cat.orm.api.web.provider;


import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: mahao
 * @date: 2021/3/6 09:52
 */
public interface ApiPojoSubjectProvider {

    /**
     * 根据请求参数返回匹配的托管apiPojo主体
     * @param request 请求体
     * @return apiPojo主体
     */
    ApiPojoSubject getPojoSubject(HttpServletRequest request);
}
