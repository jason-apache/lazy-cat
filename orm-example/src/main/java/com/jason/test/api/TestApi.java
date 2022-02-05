package com.jason.test.api;

import cool.lazy.cat.orm.api.web.bo.QueryInfo;
import cool.lazy.cat.orm.api.web.entrust.AbstractEntrustApi;
import cool.lazy.cat.orm.core.manager.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/13 12:42
 */
//@Component
public class TestApi extends AbstractEntrustApi {

    @Autowired
    public TestApi(ServiceManager serviceManager) {
        super(serviceManager);
    }

    private Map<String, Object> test(QueryInfo queryInfo) {
        System.out.println("一个测试");
        return Collections.singletonMap("test", queryInfo);
    }
}
