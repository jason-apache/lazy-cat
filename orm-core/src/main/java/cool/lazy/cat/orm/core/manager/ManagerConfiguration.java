package cool.lazy.cat.orm.core.manager;

import cool.lazy.cat.orm.core.jdbc.JdbcConfig;
import cool.lazy.cat.orm.core.manager.factory.PojoTableSubjectFactory;
import cool.lazy.cat.orm.core.manager.factory.PojoTableSubjectFactoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mahao
 * @date: 2021/3/4 21:18
 */
@Configuration
public class ManagerConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = PojoManager.class)
    public PojoManager pojoManager(JdbcConfig jdbcConfig) {
        return new PojoManager(jdbcConfig.getPojoScanBasePackages(), jdbcConfig.getPojoScanExcludePackages());
    }

    @Bean
    @ConditionalOnMissingBean(value = PojoTableSubjectFactory.class)
    public PojoTableSubjectFactory pojoTableSubjectFactory(PojoManager pojoManager) {
        return new PojoTableSubjectFactoryImpl(pojoManager);
    }

    @Bean
    @ConditionalOnMissingBean(value = PojoTableManager.class)
    public PojoTableManager pojoTableManager(PojoTableSubjectFactory pojoTableSubjectFactory) {
        return new PojoTableManager(pojoTableSubjectFactory);
    }
}
