package cool.lazy.cat.orm.core.jdbc.datasource.operation;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction.StandardTransactionTemplateManager;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction.TransactionTemplateManager;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author: mahao
 * @date: 2021/7/23 17:14
 */
public class JdbcOperationHolderImpl implements JdbcOperationHolder {

    protected final String id;
    protected final TransactionTemplateManager transactionTemplateManager;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final DatabaseDriver databaseDriver;
    protected final Dialect dialect;

    public JdbcOperationHolderImpl(String id, PlatformTransactionManager platformTransactionManager,
                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate, DatabaseDriver databaseDriver, Dialect dialect) {
        this.id = id;
        this.transactionTemplateManager = new StandardTransactionTemplateManager(platformTransactionManager);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.databaseDriver = databaseDriver;
        this.dialect = dialect;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public TransactionTemplateManager getTransactionTemplateManager() {
        return transactionTemplateManager;
    }

    @Override
    public JdbcOperations getJdbcOperations() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

    @Override
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public void release() {
    }
}
