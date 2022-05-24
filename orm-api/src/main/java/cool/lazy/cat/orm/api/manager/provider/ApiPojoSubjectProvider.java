package cool.lazy.cat.orm.api.manager.provider;

import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-05-23 22:53
 */
public interface ApiPojoSubjectProvider {

    /**
     * 提供apiPojoSubject对象
     */
    List<ApiPojoSubject> provider();
}
