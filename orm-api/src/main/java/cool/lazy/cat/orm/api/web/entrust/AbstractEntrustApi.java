package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import cool.lazy.cat.orm.core.manager.BusinessManager;
import cool.lazy.cat.orm.core.manager.subject.BusinessSubject;

/**
 * @author: mahao
 * @date: 2021/7/6 18:07
 */
public abstract class AbstractEntrustApi implements EntrustApi {

    protected final BusinessManager businessManager;

    public AbstractEntrustApi(BusinessManager businessManager) {
        this.businessManager = businessManager;
    }

    @Override
    public BusinessSubject getSubject() {
        return businessManager.getBusinessSubject(FullAutoMappingContext.getPojoType());
    }

    protected BaseService<?> getService() {
        return this.getSubject().getServiceSubject().getService();
    }
}
