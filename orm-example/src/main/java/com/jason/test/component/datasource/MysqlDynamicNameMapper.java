package com.jason.test.component.datasource;

import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectNameImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/9/15 16:39
 */
@Component
@Order(value = Integer.MAX_VALUE)
public class MysqlDynamicNameMapper implements DynamicNameMapper {

    protected Map<String, String> mappings;

    @Autowired
    public void setMappings(MultipleDataSourceConfig multipleDataSourceConfig) {
        this.mappings = multipleDataSourceConfig.getDynamicSchema().get(Constant.MYSQL);
        if (this.mappings == null) {
            this.mappings = Collections.emptyMap();
        }
    }

    @Override
    public boolean support(Class<? extends SqlType> type, Class<?> pojoType) {
        return true;
    }

    @Override
    public ObjectName map(Class<? extends SqlType> type, Class<?> pojoType, String schema, String name) {
        return new ObjectNameImpl(mappings.getOrDefault(schema, schema), name);
    }
}
