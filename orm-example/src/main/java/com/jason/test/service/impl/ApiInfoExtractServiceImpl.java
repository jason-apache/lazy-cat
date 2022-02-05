package com.jason.test.service.impl;

import com.jason.test.service.ApiInfoExtractService;
import com.jason.test.vo.ApiInfo;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.UriPojoMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-01-30 10:21
 */
@Service(value = "apiInfoExtractService")
public class ApiInfoExtractServiceImpl implements ApiInfoExtractService {

    @Autowired
    UriPojoMapping uriPojoMapping;
    @Value("#{apiConfig.apiPath}")
    protected String apiPathPrefix;

    @Override
    public List<ApiInfo> extract() {
        Map<String, Map<HttpMethod, EntryInfo>> uriMapping = uriPojoMapping.getUriMapping();
        List<ApiInfo> result = new ArrayList<>(uriMapping.size());
        for (Map.Entry<String, Map<HttpMethod, EntryInfo>> entry : uriMapping.entrySet()) {
            ApiInfo apiInfo = new ApiInfo().setApiPath(apiPathPrefix + entry.getKey());
            result.add(apiInfo);
        }
        return result;
    }

}
