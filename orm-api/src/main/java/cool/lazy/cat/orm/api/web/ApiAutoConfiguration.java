package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.ApiConfig;
import cool.lazy.cat.orm.api.web.entrust.BasicEntrustController;
import cool.lazy.cat.orm.api.web.entrust.EntrustApiImpl;
import cool.lazy.cat.orm.api.web.entrust.EntrustController;
import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.executor.DefaultApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.DefaultExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.method.MethodEntryConfiguration;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.api.web.entrust.provider.DefaultApiEntryInfoProvider;
import cool.lazy.cat.orm.core.manager.BusinessManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/4/21 19:56
 */
@Import(value = {MethodEntryConfiguration.class})
public class ApiAutoConfiguration {

    @Bean
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
    @ConditionalOnMissingBean(value = ApiMethodExecutor.class)
    public DefaultApiMethodExecutor defaultApiMethodExecutor(List<ApiMethodEntry> apiMethodEntryList) {
        return new DefaultApiMethodExecutor(apiMethodEntryList);
    }

    @Bean
    @ConditionalOnMissingBean(value = ExecutorHolder.class)
    public DefaultExecutorHolder defaultExecutorHolder(ApiMethodExecutor apiMethodExecutor) {
        return new DefaultExecutorHolder(apiMethodExecutor);
    }

    @Bean
    @ConditionalOnMissingBean(value = EntrustController.class)
    public BasicEntrustController basicEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ExecutorHolder executorHolder, ApiConfig apiConfig) {
        return new BasicEntrustController(apiEntryInfoProvider, executorHolder, apiConfig.getApiPath());
    }
}
