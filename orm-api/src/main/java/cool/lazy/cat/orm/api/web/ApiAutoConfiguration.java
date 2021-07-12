package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.ApiConfig;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.EntrustApiImpl;
import cool.lazy.cat.orm.api.web.entrust.executor.ExecutorConfiguration;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.filter.EntrustFilter;
import cool.lazy.cat.orm.api.web.entrust.method.MethodEntryConfiguration;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.api.web.entrust.provider.DefaultApiEntryInfoProvider;
import cool.lazy.cat.orm.core.manager.BusinessManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2021/4/21 19:56
 */
@Import(value = {ExecutorConfiguration.class, MethodEntryConfiguration.class})
public class ApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = EntrustApi.class)
    public EntrustApiImpl entrustApi(BusinessManager businessManager) {
        return new EntrustApiImpl(businessManager);
    }

    @Bean
    public UriPojoMapping uriPojoMapping() {
        return new UriPojoMapping();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiEntryInfoProvider.class)
    public DefaultApiEntryInfoProvider defaultApiEntryInfoProvider(UriPojoMapping uriPojoMapping) {
        return new DefaultApiEntryInfoProvider(uriPojoMapping);
    }

    @Bean
    public FilterRegistrationBean<EntrustFilter> entrustFilterFilterRegistrationBean(ApiEntryInfoProvider apiEntryInfoProvider, ExecutorHolder executorHolder, ApiConfig apiConfig) {
        FilterRegistrationBean<EntrustFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EntrustFilter(apiEntryInfoProvider, executorHolder, apiConfig.getEntrustPathNode()));
        registrationBean.addUrlPatterns(ApiConstant.PATH_SYMBOL + apiConfig.getEntrustPathNode() + "/*");
        registrationBean.setOrder(ApiConstant.FILTER_BASE_ORDER);
        return registrationBean;
    }
}
