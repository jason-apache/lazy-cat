package com.jason.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.method.AbstractApiMethodEntry;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/13 12:41
 */
@Component
public class TestApiEntry extends AbstractApiMethodEntry {

    @Autowired
    public TestApiEntry(@Qualifier("testApi") EntrustApi api, ObjectMapper objectMapper) {
        super(api, objectMapper, "test", QueryInfo.class);
    }

    @Override
    public Object[] buildParameters(HttpServletRequest request, HttpServletResponse response) {
        return new Object[]{super.readJsonObj(request, super.objectMapper.getTypeFactory().constructType(QueryInfo.class))};
    }
}
