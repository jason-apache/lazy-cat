package cool.lazy.cat.orm.api.manager;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mahao
 * @date: 2021/3/4 21:18
 */
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE)
public class WebManagerConfiguration {

    @Bean
    public ApiPojoManagerImpl apiPojoManager() {
        return new ApiPojoManagerImpl();
    }
}
