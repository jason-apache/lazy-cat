package com.jason.test.component.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-02-03 15:07
 */
@ConfigurationProperties(value = "com.jason.test.multiple-data-source")
public class MultipleDataSourceConfig {

    private Map<String, DataSourceProperties> config = Collections.emptyMap();

    public Map<String, DataSourceProperties> getConfig() {
        return config;
    }

    public void setConfig(Map<String, DataSourceProperties> config) {
        this.config = config;
    }
}
