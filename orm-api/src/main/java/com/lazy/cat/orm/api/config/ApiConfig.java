package com.lazy.cat.orm.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: mahao
 * @date: 2021/3/14 11:59
 */
@ConfigurationProperties(prefix = "cool.lazy-cat.servlet")
public class ApiConfig {

    private String entrustPath = "lazy-cat";

    public String getEntrustPath() {
        return entrustPath;
    }

    public ApiConfig setEntrustPath(String entrustPath) {
        this.entrustPath = entrustPath;
        return this;
    }
}
