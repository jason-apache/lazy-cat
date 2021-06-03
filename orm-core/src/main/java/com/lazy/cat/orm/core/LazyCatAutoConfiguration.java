package com.lazy.cat.orm.core;


import com.lazy.cat.orm.core.base.repository.impl.BaseRepositoryImpl;
import com.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import com.lazy.cat.orm.core.jdbc.JdbcComponentConfiguration;
import com.lazy.cat.orm.core.jdbc.JdbcConfig;
import com.lazy.cat.orm.core.manager.ManagerConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2021/4/21 19:23
 * 自动装配配置类
 */
@EnableConfigurationProperties(value = JdbcConfig.class)
@Import(value = {JdbcComponentConfiguration.class, ManagerConfiguration.class})
public class LazyCatAutoConfiguration {

    @Bean
    public BaseRepositoryImpl baseRepository() {
        return new BaseRepositoryImpl();
    }

    @Bean
    public BaseServiceImpl baseService() {
        return new BaseServiceImpl();
    }
}
