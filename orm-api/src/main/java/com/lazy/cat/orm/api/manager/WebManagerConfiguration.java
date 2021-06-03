package com.lazy.cat.orm.api.manager;

import com.lazy.cat.orm.core.manager.BusinessManager;
import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/3/4 21:18
 */
public class WebManagerConfiguration {

    /**
     * 注册ApiPojoManager
     * @param businessManager 等待业务管理器加载完毕再加载ApiPojoManager
     * @return ApiPojoManager
     */
    @Bean
    public ApiPojoManager apiPojoManager(BusinessManager businessManager) {
        return new ApiPojoManager();
    }
}
