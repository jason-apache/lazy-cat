package cool.lazy.cat.orm.core.manager;

import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import cool.lazy.cat.orm.core.manager.factory.PojoTableSubjectFactory;
import cool.lazy.cat.orm.core.manager.subject.PojoTableSubject;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/28 15:50
 * pojo表映射关系托管
 */
public class PojoTableManager implements Manager<PojoTableSubject> {

    protected Map<Class<?>, PojoTableSubject> pojoTableSubjectMap;
    protected static PojoTableManager defaultInstance = null;

    public PojoTableManager(PojoTableSubjectFactory pojoTableSubjectFactory) {
        this.pojoTableSubjectMap = pojoTableSubjectFactory.create().stream().collect(Collectors.toMap(PojoTableSubject::getPojoType, Function.identity()));
        defaultInstance = this;
    }

    public static PojoTableManager getDefaultInstance() {
        return defaultInstance;
    }

    @Override
    public PojoTableSubject getByPojoType(Class<?> pojoType) {
        PojoTableSubject subject = pojoTableSubjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }
}
