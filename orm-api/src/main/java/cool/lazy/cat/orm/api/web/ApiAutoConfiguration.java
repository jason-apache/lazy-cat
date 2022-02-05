package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.ApiConfig;
import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.service.CommonApiService;
import cool.lazy.cat.orm.api.service.impl.CommonApiServiceImpl;
import cool.lazy.cat.orm.api.web.entrust.BasicEntrustController;
import cool.lazy.cat.orm.api.web.entrust.EntrustController;
import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.executor.DefaultApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.executor.intercepter.ApiMethodExecuteInterceptor;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.api.web.entrust.provider.DefaultApiEntryInfoProvider;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/4/21 19:56
 */
@Import(value = {ApiMethodAutoConfiguration.class})
public class ApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = CommonApiService.class)
    public CommonApiService commonApiService(BaseRepository baseRepository, PojoTableManager pojoTableManager, ApiPojoManager apiPojoManager) {
        return new CommonApiServiceImpl(baseRepository, pojoTableManager, apiPojoManager);
    }

    @Bean
    @ConditionalOnMissingBean(value = UriPojoMapping.class)
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
    public DefaultApiMethodExecutor defaultApiMethodExecutor(List<ApiMethodEntry> apiMethodEntryList, @Autowired(required = false)List<ApiMethodExecuteInterceptor> interceptorList) {
        return new DefaultApiMethodExecutor(apiMethodEntryList, interceptorList);
    }

    @Bean
    @ConditionalOnMissingBean(value = EntrustController.class)
    public BasicEntrustController basicEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ApiMethodExecutor apiMethodExecutor, ApiConfig apiConfig) {
        return new BasicEntrustController(apiEntryInfoProvider, apiMethodExecutor, apiConfig.getApiPath());
    }
}
