package cool.lazy.cat.orm.core.manager.subject;


import cool.lazy.cat.orm.core.base.service.BaseService;

/**
 * @author: mahao
 * @date: 2021/3/6 17:09
 */
public class ServiceSubject implements Subject {

    protected BaseService<?> service;

    public BaseService<?> getService() {
        return service;
    }

    public void setService(BaseService<?> service) {
        this.service = service;
    }
}
