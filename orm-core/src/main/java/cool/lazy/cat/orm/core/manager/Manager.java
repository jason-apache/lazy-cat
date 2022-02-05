package cool.lazy.cat.orm.core.manager;

import cool.lazy.cat.orm.core.manager.subject.Subject;

/**
 * @author: mahao
 * @date: 2021/3/6 17:02
 */
public interface Manager<S extends Subject> {

    S getByPojoType(Class<?> pojoType);
}
