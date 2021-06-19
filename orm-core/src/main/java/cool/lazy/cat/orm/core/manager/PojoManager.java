package cool.lazy.cat.orm.core.manager;


import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/4 20:03
 * pojo托管
 */
public class PojoManager implements Manager {

    public static List<Class<?>> pojoClasses = Collections.emptyList();

    private final List<PojoSubject> pojoSubjectList = new ArrayList<>();

    public PojoManager() {
        for (Class<?> pojoClass : pojoClasses) {
            PojoSubject subject = new PojoSubject();
            subject.setPojoName(pojoClass.getName());
            subject.setPojoType(pojoClass);
            this.pojoSubjectList.add(subject);
        }
    }

    public List<PojoSubject> getPojoSubjectList() {
        return this.pojoSubjectList;
    }

    /**
     * 根据pojo完全限定名返回pojo信息
     * @param pojoName pojo名称
     * @return pojo主体信息
     */
    public PojoSubject getByName(String pojoName) {
        for (PojoSubject subject : this.pojoSubjectList) {
            if (Objects.equals(pojoName, subject.getPojoName())) {
                return subject;
            }
        }
        throw new UnKnowPojoException("未定义的pojo类型：" + pojoName);
    }

    /**
     * 根据pojo类型返回pojo信息
     * @param pojoType pojo类型
     * @return pojo主体信息
     */
    public PojoSubject getByPojoType(Class<?> pojoType) {
        for (PojoSubject subject : this.pojoSubjectList) {
            if (pojoType == subject.getPojoType()) {
                return subject;
            }
        }
        throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
    }
}
