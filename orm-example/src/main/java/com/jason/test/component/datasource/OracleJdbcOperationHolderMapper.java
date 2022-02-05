package com.jason.test.component.datasource;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.jdbc.adapter.DialectAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.JdbcOperationHolderMapper;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolderImpl;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/27 15:37
 * 自定义数据源
 */
@Component
@ConditionalOnBean(value = MultipleDataSourceRegistry.class)
public class OracleJdbcOperationHolderMapper implements JdbcOperationHolderMapper {

    private JdbcOperationHolder holder;

    public OracleJdbcOperationHolderMapper(
            DialectAdapter dialectAdapter, @Qualifier(Constant.ORACLE) javax.sql.DataSource dataSource,
            @Qualifier(Constant.ORACLE + "PlatformTransactionManager") PlatformTransactionManager platformTransactionManager) {
        this.init(dialectAdapter.adapt(DatabaseDriver.ORACLE), dataSource, platformTransactionManager);
    }

    @Override
    public boolean matched(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params) {
        DataSource dataSource = pojoType.getAnnotation(DataSource.class);
        return null != dataSource && Constant.ORACLE.equals(dataSource.id());
    }

    @Override
    public JdbcOperationHolder get(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params) {
        return holder;
    }

    private void init(Dialect dialect, javax.sql.DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.holder = new JdbcOperationHolderImpl(Constant.ORACLE, platformTransactionManager, namedParameterJdbcTemplate, DatabaseDriver.ORACLE, dialect);
    }
}
