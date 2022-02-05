package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.web.FullAutoMappingContext;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.manager.ServiceManager;
import cool.lazy.cat.orm.core.manager.subject.ServiceSubject;

/**
 * @author: mahao
 * @date: 2021/7/6 18:07
 */
public abstract class AbstractEntrustApi implements EntrustApi {

    protected final ServiceManager serviceManager;

    public AbstractEntrustApi(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public ServiceSubject getSubject() {
        return serviceManager.getByPojoType(FullAutoMappingContext.getPojoType());
    }

    protected BaseService<?> getService() {
        return this.getSubject().getService();
    }
}
