package cool.lazy.cat.orm.core.jdbc.manager;

import cool.lazy.cat.orm.core.jdbc.manager.factory.PojoTableSubjectFactory;
import cool.lazy.cat.orm.core.jdbc.manager.subject.PojoTableSubject;
import cool.lazy.cat.orm.core.manager.Manager;
import cool.lazy.cat.orm.core.manager.PojoManager;
import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/28 15:50
 * pojo表映射关系托管
 */
public class PojoTableManager implements Manager {

    @Autowired
    protected PojoTableSubjectFactory pojoTableSubjectFactory;
    protected Map<Class<?>, PojoTableSubject> pojoTableSubjectMap;

    @Autowired
    public void initPojoTableSubject(PojoManager pojoManager) {
        List<PojoSubject> pojoSubjectList = pojoManager.getPojoSubjectList();
        pojoTableSubjectMap = new HashMap<>(pojoSubjectList.size());
        for (PojoSubject pojoSubject : pojoSubjectList) {
            Class<?> pojoType = pojoSubject.getPojoType();
            PojoTableSubject subject = (PojoTableSubject) pojoTableSubjectFactory.build(pojoType);
            pojoTableSubjectMap.put(pojoType, subject);
        }
        // 初始化联查条件
        pojoTableSubjectFactory.initJoinCondition(pojoTableSubjectMap);
        // 初始化表链式调用结构
        pojoTableSubjectFactory.analysisTableChain(pojoTableSubjectMap);
    }

    public PojoTableSubject getByPojoType(Class<?> pojoType) {
        PojoTableSubject subject = pojoTableSubjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }

    public Map<Class<?>, PojoTableSubject> getAllSubjectMap() {
        return pojoTableSubjectMap;
    }
}
