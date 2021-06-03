package com.lazy.cat.orm.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazy.cat.orm.api.interceptor.EntrustRequestInterceptor;
import com.lazy.cat.orm.api.web.constant.ApiConstant;
import com.lazy.cat.orm.api.web.provider.ApiPojoSubjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/7 14:08
 * 自动映射配置类
 */
public class FullAutoMappingApiConfiguration implements WebMvcConfigurer {

    @Autowired
    private ApiPojoSubjectProvider apiPojoSubjectProvider;
    @Value("#{apiConfig.entrustPath}")
    protected String entrustEntry;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new FullyAutomaticArgumentResolver(apiPojoSubjectProvider, objectMapper));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EntrustRequestInterceptor(apiPojoSubjectProvider)).order(0)
                .addPathPatterns(ApiConstant.PATH_SYMBOL + entrustEntry + "/**");
    }
}
