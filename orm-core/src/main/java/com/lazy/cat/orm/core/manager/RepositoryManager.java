package com.lazy.cat.orm.core.manager;

import com.lazy.cat.orm.core.base.repository.BaseRepository;
import com.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import com.lazy.cat.orm.core.manager.subject.PojoSubject;
import com.lazy.cat.orm.core.manager.subject.RepositorySubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/4 17:58
 */
public class RepositoryManager implements Manager {

    @Autowired
    protected BaseRepository<?> baseRepository;
    protected Map<Class<?>, RepositorySubject> repositorySubjectMap;

    @Autowired
    public void initRepositoryMap(PojoManager pojoManager, List<BaseRepository<?>> repositoryList) {
        List<PojoSubject> pojoSubjectList = pojoManager.getPojoSubjectList();
        repositorySubjectMap = new HashMap<>(pojoSubjectList.size());
        for (PojoSubject pojoSubject : pojoSubjectList) {
            Class<?> pojoType = pojoSubject.getPojoType();
            RepositorySubject subject = new RepositorySubject();
            // 设置一个默认的repository
            subject.setPojoSubject(pojoSubject).setPojoType(pojoType).setRepository(baseRepository);
            repositoryList.stream().filter(r -> r.getPojoType() == subject.getPojoType()).findFirst().ifPresent(subject::setRepository);
            repositorySubjectMap.put(pojoType, subject);
        }
    }

    public RepositorySubject getByPojoType(Class<?> pojoType) {
        RepositorySubject subject = this.repositorySubjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }
}
