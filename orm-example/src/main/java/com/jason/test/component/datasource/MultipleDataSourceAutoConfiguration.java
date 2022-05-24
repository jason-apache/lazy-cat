package com.jason.test.component.datasource;

import com.jason.test.constant.Constant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author: mahao
 * @date: 2022-02-03 15:07
 */
@Configuration
@EnableConfigurationProperties(value = MultipleDataSourceConfig.class)
@ConditionalOnBean(value = MultipleDataSourceRegistry.class)
public class MultipleDataSourceAutoConfiguration {

    @Bean(name = Constant.MYSQL)
    public DataSource mysqlTest(MultipleDataSourceConfig multipleDataSourceConfig) {
        return multipleDataSourceConfig.getDataSourceConfig().get(Constant.MYSQL).initializeDataSourceBuilder().build();
    }

    @Bean(name = Constant.MYSQL + "PlatformTransactionManager")
    public PlatformTransactionManager mysqlPlatformTransactionManager(@Qualifier(value = Constant.MYSQL) DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean(name = Constant.ORACLE)
    public DataSource oracleTest(MultipleDataSourceConfig multipleDataSourceConfig) {
        return multipleDataSourceConfig.getDataSourceConfig().get(Constant.ORACLE).initializeDataSourceBuilder().build();
    }

    @Bean(name = Constant.ORACLE + "PlatformTransactionManager")
    public PlatformTransactionManager oraclePlatformTransactionManager(@Qualifier(value = Constant.ORACLE) DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean(name = Constant.LOCAL_PG)
    public DataSource localhostPgsql(MultipleDataSourceConfig multipleDataSourceConfig) {
        return multipleDataSourceConfig.getDataSourceConfig().get(Constant.LOCAL_PG).initializeDataSourceBuilder().build();
    }

    @Bean(name = Constant.LOCAL_PG + "PlatformTransactionManager")
    public PlatformTransactionManager localPgsqlPlatformTransactionManager(@Qualifier(value = Constant.LOCAL_PG) DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
