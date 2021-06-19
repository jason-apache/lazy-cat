package cool.lazy.cat.orm.core.manager.subject;


import cool.lazy.cat.orm.core.base.service.BaseService;

/**
 * @author: mahao
 * @date: 2021/3/6 17:09
 */
public class ServiceSubject {

    /**
     * 记录上下文中对应的service
     */
    protected BaseService<?> service;

    public BaseService<?> getService() {
        return service;
    }

    public void setService(BaseService<?> service) {
        this.service = service;
    }
}
