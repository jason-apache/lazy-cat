package cool.lazy.cat.orm.api.manager;

import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.core.manager.Manager;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021-11-16 15:47
 */
public interface ApiPojoManager extends Manager<ApiPojoSubject> {

    List<ApiPojoSubject> getApiPojoSubjectList();

    ApiPojoSubject getByNameSpace(String nameSpace);
}
