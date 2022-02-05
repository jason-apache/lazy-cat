package cool.lazy.cat.orm.core.jdbc.datasource.operation;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction.TransactionTemplateManager;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author: mahao
 * @date: 2021/7/23 17:12
 * jdbc操作持有者 可扩展多数据源
 */
public interface JdbcOperationHolder {

    /**
     * @return 唯一标识
     */
    String getId();

    /**
     * @return 事务模板管理
     */
    TransactionTemplateManager getTransactionTemplateManager();

    /**
     * @return jdbc操作对象
     */
    JdbcOperations getJdbcOperations();

    /**
     * @return jdbc操作对象
     */
    NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

    /**
     * @return jdbc连接类型
     */
    DatabaseDriver getDatabaseDriver();

    /**
     * @return 方言
     */
    Dialect getDialect();

    /**
     * 执行sql操作后释放资源的动作
     */
    void release();
}
