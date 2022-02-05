package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectNameImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/28 17:19
 */
public class DynamicNameAdapterImpl implements DynamicNameAdapter {

    protected final List<DynamicNameMapper> dynamicNameMapperList;

    public DynamicNameAdapterImpl(List<DynamicNameMapper> dynamicNameMapperList) {
        this.dynamicNameMapperList = dynamicNameMapperList == null ? new ArrayList<>() : dynamicNameMapperList;
    }

    @Override
    public ObjectName adapt(Class<? extends SqlType> type, Class<?> pojoType, String schema, String name) {
        for (DynamicNameMapper schemaMapping : dynamicNameMapperList) {
            if (schemaMapping.support(type, pojoType)) {
                return schemaMapping.map(type, pojoType, schema, name);
            }
        }
        return new ObjectNameImpl(schema, name);
    }
}
