package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/7/28 17:20
 * 动态名称映射器
 */
public interface DynamicNameMapper {

    boolean support(Class<? extends SqlType> type, Class<?> pojoType);

    /**
     * 映射对象名称
     * @param type sql操作类型
     * @param pojoType pojo类型
     * @param schema 数据库对象schema
     * @param name 数据库对象名称
     * @return 动态名称
     */
    ObjectName map(Class<? extends SqlType> type, Class<?> pojoType, String schema, String name);
}
