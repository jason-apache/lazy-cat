package cool.lazy.cat.orm.core.jdbc.datasource.operation;

import cool.lazy.cat.orm.core.jdbc.exception.NoVisibleJdbcOperationHolderException;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;

/**
 * @author: mahao
 * @date: 2021/11/1 11:02
 * jdbc操作上下文
 */
public final class JdbcOperationSupport {

    private static final ThreadLocal<JdbcOperationHolder> JDBC_OPERATION_HOLDER_CONTEXT = new ThreadLocal<>();

    public static JdbcOperationHolder get() {
        return JDBC_OPERATION_HOLDER_CONTEXT.get();
    }

    public static JdbcOperationHolder getAndCheck() {
        JdbcOperationHolder jdbcOperationHolder = JDBC_OPERATION_HOLDER_CONTEXT.get();
        if (null == jdbcOperationHolder) {
            throw new NoVisibleJdbcOperationHolderException("上下文中未初始化jdbcOperationHolder");
        }
        return jdbcOperationHolder;
    }

    public static Dialect getDialect() {
        return get().getDialect();
    }

    public static void set(JdbcOperationHolder holder) {
        JDBC_OPERATION_HOLDER_CONTEXT.set(holder);
    }

    public static void clear() {
        JDBC_OPERATION_HOLDER_CONTEXT.remove();
    }
}
