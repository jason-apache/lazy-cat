package com.jason.test.component.datasource;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectNameImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-01-20 11:07
 */
@Component
@ConditionalOnBean(value = MultipleDataSourceRegistry.class)
public class LocalPgsqlNameMapper implements DynamicNameMapper {

    protected Map<String, String> mappings;

    @Autowired
    public void setMappings(MultipleDataSourceConfig multipleDataSourceConfig) {
        this.mappings = multipleDataSourceConfig.getDynamicSchema().get(Constant.LOCAL_PG);
        if (this.mappings == null) {
            this.mappings = Collections.emptyMap();
        }
    }

    @Override
    public boolean support(Class<? extends SqlType> type, Class<?> pojoType) {
        DataSource dataSource = pojoType.getAnnotation(DataSource.class);
        return null != dataSource && Constant.LOCAL_PG.equals(dataSource.id());
    }

    @Override
    public ObjectName map(Class<? extends SqlType> type, Class<?> pojoType, String schema, String name) {
        return new ObjectNameImpl(mappings.getOrDefault(schema, schema), name);
    }
}
