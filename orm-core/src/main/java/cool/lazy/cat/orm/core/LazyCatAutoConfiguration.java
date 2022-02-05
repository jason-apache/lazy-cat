package cool.lazy.cat.orm.core;


import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.repository.impl.BaseRepositoryImpl;
import cool.lazy.cat.orm.core.jdbc.JdbcComponentAutoConfiguration;
import cool.lazy.cat.orm.core.jdbc.JdbcConfig;
import cool.lazy.cat.orm.core.jdbc.component.executor.ComponentExecutor;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutor;
import cool.lazy.cat.orm.core.manager.ManagerConfiguration;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/4/21 19:23
 * 自动装配配置类
 */
@EnableConfigurationProperties(value = JdbcConfig.class)
@Configuration
@Import(value = {ManagerConfiguration.class, JdbcComponentAutoConfiguration.class, ServiceBeanAutoRegistry.class})
public class LazyCatAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = BaseRepository.class)
    public BaseRepository baseRepository(SqlExecutor sqlExecutor, PojoTableManager pojoTableManager, IdGeneratorProvider idGeneratorProvider,
                                         List<ComponentExecutor> componentExecutorList, JdbcOperationHolderAdapter jdbcOperationHolderAdapter) {
        return new BaseRepositoryImpl(sqlExecutor, pojoTableManager, idGeneratorProvider, componentExecutorList, jdbcOperationHolderAdapter);
    }
}
