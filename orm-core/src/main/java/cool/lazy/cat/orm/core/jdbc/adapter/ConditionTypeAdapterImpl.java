package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.adapter.mapper.ConditionTypeMapper;
import cool.lazy.cat.orm.core.jdbc.exception.executor.CannotResolveConditionTypeException;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/7/19 16:21
 */
public class ConditionTypeAdapterImpl implements ConditionTypeAdapter {

    protected final List<ConditionTypeMapper> conditionTypeMapperList;
    protected final Map<Class<? extends ConditionType>, ConditionTypeMapper> conditionTypeMapCache;

    public ConditionTypeAdapterImpl(List<ConditionTypeMapper> conditionTypeMapperList) {
        this.conditionTypeMapperList = conditionTypeMapperList;
        this.conditionTypeMapCache = new ConcurrentHashMap<>(conditionTypeMapperList.size());
    }

    @Override
    public ConditionExpressionSqlString adapt(ConditionType type, String paramName, Object val) {
        ConditionTypeMapper mapping = conditionTypeMapCache.get(type.getClass());
        if (null != mapping) {
            return mapping.map(type, paramName, val);
        }
        for (ConditionTypeMapper conditionTypeMapper : conditionTypeMapperList) {
            if (conditionTypeMapper.matched(type)) {
                conditionTypeMapCache.put(type.getClass(), conditionTypeMapper);
                return conditionTypeMapper.map(type, paramName, val);
            }
        }
        throw new CannotResolveConditionTypeException("无法处理的sql条件类型: " + type.getClass());
    }
}
