package cool.lazy.cat.orm.core.jdbc.sql;

/**
 * @author: mahao
 * @date: 2021/7/23 10:24
 */
public interface MetaInfo {

    Object get(String name);

    void set(String name, Object val);
}
