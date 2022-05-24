package com.jason.test.component.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-02-03 15:07
 */
@ConfigurationProperties(value = "custom.data-source")
public class MultipleDataSourceConfig {

    /**
     * 数据源配置
     */
    private Map<String, DataSourceProperties> dataSourceConfig = Collections.emptyMap();
    /**
     * 动态schema配置
     */
    private Map<String, Map<String, String>> dynamicSchema = Collections.emptyMap();

    public Map<String, DataSourceProperties> getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(Map<String, DataSourceProperties> dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public Map<String, Map<String, String>> getDynamicSchema() {
        return dynamicSchema;
    }

    public void setDynamicSchema(Map<String, Map<String, String>> dynamicSchema) {
        this.dynamicSchema = dynamicSchema;
    }
}
