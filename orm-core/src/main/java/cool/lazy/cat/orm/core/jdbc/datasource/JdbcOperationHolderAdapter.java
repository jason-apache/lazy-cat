package cool.lazy.cat.orm.core.jdbc.datasource;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/20 17:54
 * jdbc操作适配器
 */
public interface JdbcOperationHolderAdapter {

    default JdbcOperationHolder adapt(Class<? extends SqlType> sqlType, Class<?> pojoType) {
        return this.adapt(sqlType, pojoType, Collections.emptyMap());
    }

    /**
     * 根据参数适配对应的jdbc操作对象
     * @param sqlType sql操作类型
     * @param pojoType pojo对象类型
     * @param params 参数
     * @return jdbc操作对象
     */
    JdbcOperationHolder adapt(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params);
}
