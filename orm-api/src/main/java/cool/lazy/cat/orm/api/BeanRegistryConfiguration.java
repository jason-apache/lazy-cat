package cool.lazy.cat.orm.api;

import cool.lazy.cat.orm.api.manager.WebManagerConfiguration;
import cool.lazy.cat.orm.api.web.ApiAutoConfiguration;
import cool.lazy.cat.orm.core.base.service.BaseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2022-01-19 11:27
 * 控制整个api模块的bean注册
 */
@ConditionalOnExpression("${cool.lazy-cat.servlet.enabled:true}")
@ConditionalOnBean(value = {BaseService.class})
@Import(value = {WebManagerConfiguration.class, ApiAutoConfiguration.class})
public class BeanRegistryConfiguration {
}
