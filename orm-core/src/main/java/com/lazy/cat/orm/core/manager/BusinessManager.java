package com.lazy.cat.orm.core.manager;

import com.lazy.cat.orm.core.base.constant.Constant;
import com.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import com.lazy.cat.orm.core.manager.subject.BusinessSubject;
import com.lazy.cat.orm.core.manager.subject.PojoSubject;
import com.lazy.cat.orm.core.manager.subject.RepositorySubject;
import com.lazy.cat.orm.core.manager.subject.ServiceSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/4 20:20
 */
public class BusinessManager implements Manager {

    @Autowired
    protected PojoManager pojoManager;
    @Autowired
    protected ServiceManager serviceManager;
    @Autowired
    protected RepositoryManager repositoryManager;

    private final Map<Class<?>, BusinessSubject> subjectMap = new HashMap<>(Constant.DEFAULT_CONTAINER_SIZE);

    @Autowired
    public void initBusinessSubject(PojoManager pojoManager, RepositoryManager repositoryManager, ServiceManager serviceManager) {
        List<PojoSubject> pojoSubjectList = pojoManager.getPojoSubjectList();
        for (PojoSubject pojoSubject : pojoSubjectList) {
            BusinessSubject subject = new BusinessSubject();
            subject.setPojoType(pojoSubject);
            ServiceSubject serviceSubject = serviceManager.getByPojoType(pojoSubject.getPojoType());
            subject.setServiceSubject(serviceSubject);
            subject.setRepositorySubject(repositoryManager.getByPojoType(pojoSubject.getPojoType()));
            subjectMap.put(pojoSubject.getPojoType(), subject);
        }
    }

    public BusinessSubject getBusinessSubject(Class<?> pojoType) {
        BusinessSubject subject = subjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }

    public ServiceSubject getServiceSubject(Class<?> pojoType) {
        ServiceSubject subject = subjectMap.get(pojoType).getServiceSubject();
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }

    public RepositorySubject getRepositorySubject(Class<?> pojoType) {
        RepositorySubject subject = subjectMap.get(pojoType).getRepositorySubject();
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }
}
