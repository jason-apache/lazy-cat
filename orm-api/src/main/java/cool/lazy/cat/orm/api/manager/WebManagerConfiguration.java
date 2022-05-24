package cool.lazy.cat.orm.api.manager;

import cool.lazy.cat.orm.api.ApiConfig;
import cool.lazy.cat.orm.api.manager.provider.ApiPojoSubjectProvider;
import cool.lazy.cat.orm.api.manager.provider.ConfigFileApiPojoSubjectProvider;
import cool.lazy.cat.orm.api.manager.provider.MetaAnnotationApiPojoSubjectProvider;
import cool.lazy.cat.orm.core.manager.PojoManager;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mahao
 * @date: 2021/3/4 21:18
 */
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE)
public class WebManagerConfiguration {

    @Bean
    public ApiPojoManagerImpl apiPojoManager() {
        return new ApiPojoManagerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiPojoSubjectProvider.class)
    public ApiPojoSubjectProvider apiPojoSubjectProvider(PojoManager pojoManager, PojoTableManager pojoTableManager, ApiConfig apiConfig) {
        if (apiConfig.getApiPojoSubjectRegistryInstance().equals(MetaAnnotationApiPojoSubjectProvider.class)) {
            return new MetaAnnotationApiPojoSubjectProvider(pojoManager, pojoTableManager);
        } else {
            return new ConfigFileApiPojoSubjectProvider(apiConfig.getApiEntries());
        }
    }
}
