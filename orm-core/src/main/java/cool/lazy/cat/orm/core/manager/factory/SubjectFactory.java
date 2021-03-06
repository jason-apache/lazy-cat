package cool.lazy.cat.orm.core.manager.factory;


import cool.lazy.cat.orm.core.manager.subject.Subject;

/**
 * @author: mahao
 * @date: 2021/3/11 12:55
 */
public interface SubjectFactory {

    Subject build(Class<?> pojoType);
}
