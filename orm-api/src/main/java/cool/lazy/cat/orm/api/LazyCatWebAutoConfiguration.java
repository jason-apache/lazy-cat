package cool.lazy.cat.orm.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2021/4/21 19:53
 * Api模块自动装配配置类
 */
@EnableConfigurationProperties(value = ApiConfig.class)
@Import(value = {BeanRegistryConfiguration.class})
public class LazyCatWebAutoConfiguration {

    @Bean
    public ApiConfig apiConfig() {
        return new ApiConfig();
    }
}
