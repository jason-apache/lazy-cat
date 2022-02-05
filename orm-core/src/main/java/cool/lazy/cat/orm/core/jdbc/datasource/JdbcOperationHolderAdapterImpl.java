package cool.lazy.cat.orm.core.jdbc.datasource;

import cool.lazy.cat.orm.core.jdbc.adapter.mapper.JdbcOperationHolderMapper;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.exception.executor.NoMatchedJdbcOperationHolderFoundException;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/20 17:55
 */
public class JdbcOperationHolderAdapterImpl implements JdbcOperationHolderAdapter {

    protected final List<JdbcOperationHolderMapper> jdbcOperationHolderMapperList;

    public JdbcOperationHolderAdapterImpl(List<JdbcOperationHolderMapper> jdbcOperationHolderMapperList) {
        this.jdbcOperationHolderMapperList = jdbcOperationHolderMapperList;
    }

    @Override
    public JdbcOperationHolder adapt(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params) {
        for (JdbcOperationHolderMapper jdbcOperationHolderMapper : jdbcOperationHolderMapperList) {
            if (jdbcOperationHolderMapper.matched(sqlType, pojoType, params)) {
                return jdbcOperationHolderMapper.get(sqlType, pojoType, params);
            }
        }
        throw new NoMatchedJdbcOperationHolderFoundException("找不到适配的JdbcOperationHolder \t" + sqlType.getName() + ":" + pojoType.getName());
    }
}
