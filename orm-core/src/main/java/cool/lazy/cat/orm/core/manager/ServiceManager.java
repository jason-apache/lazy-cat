package cool.lazy.cat.orm.core.manager;

import cool.lazy.cat.orm.core.base.constant.Constant;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.manager.subject.ServiceSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/4 17:58
 */
public class ServiceManager {

    private ServiceSubject defaultServiceSubject;
    protected Map<Class<?>, ServiceSubject> serviceSubjectMap;

    @Autowired
    public void initServiceMap(List<BaseService<?>> serviceList, BaseService<Object> baseService) {
        defaultServiceSubject = new ServiceSubject();
        defaultServiceSubject.setService(baseService);
        serviceSubjectMap = new HashMap<>(Constant.DEFAULT_CONTAINER_SIZE);
        serviceList = serviceList.stream().filter(s -> s.getPojoType() != null).collect(Collectors.toList());
        for (BaseService<?> service : serviceList) {
            ServiceSubject subject = new ServiceSubject();
            subject.setService(service);
            serviceSubjectMap.put(service.getPojoType(), subject);
        }
    }

    public ServiceSubject getByPojoType(Class<?> pojoType) {
        ServiceSubject subject = serviceSubjectMap.get(pojoType);
        if (null != subject) {
            return subject;
        }
        return defaultServiceSubject;
    }
}
