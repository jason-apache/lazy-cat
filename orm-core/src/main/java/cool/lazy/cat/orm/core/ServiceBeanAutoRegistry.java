package cool.lazy.cat.orm.core;

import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.service.CommonService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import cool.lazy.cat.orm.core.base.service.impl.CommonServiceImpl;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import cool.lazy.cat.orm.core.manager.ServiceManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author: mahao
 * @date: 2022-02-05 16:58
 */
@ConditionalOnExpression("${cool.lazy-cat.jdbc.enable-service-registry:true}")
public class ServiceBeanAutoRegistry {

    @Bean
    @Primary
    public BaseService<Object> baseService() {
        return new BaseServiceImpl<>();
    }

    @Bean
    @ConditionalOnMissingBean(value = CommonService.class)
    public CommonService commonService(BaseRepository baseRepository, PojoTableManager pojoTableManager) {
        return new CommonServiceImpl(baseRepository, pojoTableManager);
    }

    @Bean
    @ConditionalOnMissingBean(value = ServiceManager.class)
    public ServiceManager serviceManager() {
        return new ServiceManager();
    }
}
