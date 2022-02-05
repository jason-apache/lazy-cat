package com.jason.test.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/9/15 17:57
 */
@Component
@ConfigurationProperties(prefix = "custom.dynamic-schema")
public class DynamicSchemaConfig {

    private Map<String, Map<String, String>> content;

    public Map<String, Map<String, String>> getContent() {
        return content;
    }

    public void setContent(Map<String, Map<String, String>> content) {
        this.content = content;
    }
}
