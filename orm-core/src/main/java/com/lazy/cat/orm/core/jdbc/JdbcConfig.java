package com.lazy.cat.orm.core.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/14 12:10
 */
@ConfigurationProperties(prefix = "lazy-cat.jdbc")
public class JdbcConfig {

    private boolean uppercase;
    @Value("${spring.datasource.driver-class-name:null}")
    private Class<?> driverClass;
    private Class<?> dialectClass;
    private Map<String, String> dbSchema = new HashMap<>();

    public boolean isUppercase() {
        return uppercase;
    }

    public JdbcConfig setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
        return this;
    }

    public Class<?> getDriverClass() {
        return driverClass;
    }

    public JdbcConfig setDriverClass(Class<?> driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public Class<?> getDialectClass() {
        return dialectClass;
    }

    public JdbcConfig setDialectClass(Class<?> dialectClass) {
        this.dialectClass = dialectClass;
        return this;
    }

    public Map<String, String> getDbSchema() {
        return dbSchema;
    }

    public JdbcConfig setDbSchema(Map<String, String> dbSchema) {
        this.dbSchema = dbSchema;
        return this;
    }
}
