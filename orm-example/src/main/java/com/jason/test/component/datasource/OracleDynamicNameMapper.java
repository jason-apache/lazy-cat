package com.jason.test.component.datasource;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import com.jason.test.component.DynamicSchemaConfig;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectNameImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/9/15 16:39
 */
@Component
@Order(value = 10)
@ConditionalOnBean(value = MultipleDataSourceRegistry.class)
public class OracleDynamicNameMapper implements DynamicNameMapper {

    protected Map<String, String> mappings;

    @Autowired
    public void setMappings(DynamicSchemaConfig dynamicSchemaConfig) {
        this.mappings = dynamicSchemaConfig.getContent().get(Constant.ORACLE);
        if (this.mappings == null) {
            this.mappings = Collections.emptyMap();
        }
    }

    @Override
    public boolean support(Class<? extends SqlType> type, Class<?> pojoType) {
        DataSource dataSource = pojoType.getAnnotation(DataSource.class);
        return null != dataSource && Constant.ORACLE.equals(dataSource.id());
    }

    @Override
    public ObjectName map(Class<? extends SqlType> type, Class<?> pojoType, String schema, String name) {
        return new ObjectNameImpl(mappings.getOrDefault(schema, schema), name);
    }
}
