package com.lazy.cat.orm.api;

import com.lazy.cat.orm.api.config.ApiConfig;
import com.lazy.cat.orm.api.config.FullAutoMappingApiConfiguration;
import com.lazy.cat.orm.api.manager.WebManagerConfiguration;
import com.lazy.cat.orm.api.web.ApiAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2021/4/21 19:53
 * Api模块自动装配配置类
 */
@EnableConfigurationProperties(value = ApiConfig.class)
@Import(value = {WebManagerConfiguration.class, ApiAutoConfiguration.class, FullAutoMappingApiConfiguration.class})
public class LazyCatWebAutoConfiguration {

    @Bean
    public ApiConfig apiConfig() {
        return new ApiConfig();
    }
}
