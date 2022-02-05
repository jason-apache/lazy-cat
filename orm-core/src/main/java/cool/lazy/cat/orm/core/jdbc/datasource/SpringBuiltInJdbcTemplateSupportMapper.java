package cool.lazy.cat.orm.core.jdbc.datasource;

import cool.lazy.cat.orm.core.jdbc.adapter.DialectAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.JdbcOperationHolderMapper;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolderImpl;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/22 11:40
 * 默认缺省的一个jdbc操作对象映射器
 * 兜底 在不配置任何jdbc操作对象映射器时也能使用spring jdbc配置文件中配置的数据源
 */
public class SpringBuiltInJdbcTemplateSupportMapper implements JdbcOperationHolderMapper {

    protected final JdbcOperationHolder jdbcOperationHolder;

    public SpringBuiltInJdbcTemplateSupportMapper(PlatformTransactionManager platformTransactionManager, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DialectAdapter dialectAdapter, DatabaseDriver databaseDriver) {
        Dialect dialect = dialectAdapter.adapt(databaseDriver);
        this.jdbcOperationHolder = new JdbcOperationHolderImpl("springBuiltInJdbcTemplate", platformTransactionManager, namedParameterJdbcTemplate, databaseDriver, dialect);
    }

    @Override
    public boolean matched(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params) {
        return true;
    }

    @Override
    public JdbcOperationHolder get(Class<? extends SqlType> sqlType, Class<?> pojoType, Map<String, Object> params) {
        return this.jdbcOperationHolder;
    }
}
