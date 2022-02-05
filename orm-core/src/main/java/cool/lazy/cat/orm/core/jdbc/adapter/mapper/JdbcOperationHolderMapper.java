package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/20 17:56
 */
public interface JdbcOperationHolderMapper {

    boolean matched(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params);

    JdbcOperationHolder get(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params);
}
