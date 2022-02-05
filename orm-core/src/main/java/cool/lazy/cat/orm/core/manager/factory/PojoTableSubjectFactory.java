package cool.lazy.cat.orm.core.manager.factory;

import cool.lazy.cat.orm.core.manager.subject.PojoTableSubject;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/15 16:34
 */
public interface PojoTableSubjectFactory extends SubjectFactory {

    /**
     * 创建PojoTableSubject
     * @return PojoTableSubject集合
     */
    Collection<PojoTableSubject> create();
}
