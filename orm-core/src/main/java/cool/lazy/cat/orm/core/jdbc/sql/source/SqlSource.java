package cool.lazy.cat.orm.core.jdbc.sql.source;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * @author: mahao
 * @date: 2021/7/19 13:37
 * sql语句参数的载体
 */
public interface SqlSource extends SqlParameterSource {

    SqlSource set(String key, Object v);

    /**
     * @return 资源对应的pojo类型
     */
    Class<?> getPojoType();
}
