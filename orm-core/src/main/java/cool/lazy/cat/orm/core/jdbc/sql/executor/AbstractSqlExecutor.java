package cool.lazy.cat.orm.core.jdbc.sql.executor;

import cool.lazy.cat.orm.core.jdbc.component.handle.SpecialColumnHandler;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.SqlInterceptor;
import cool.lazy.cat.orm.core.jdbc.sql.printer.SqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/20 15:19
 */
public abstract class AbstractSqlExecutor implements SqlExecutor {

    protected final JdbcOperationHolderAdapter jdbcOperationHolderAdapter;
    protected final List<SqlPrinter> sqlPrinterList;
    protected final List<SqlInterceptor> sqlInterceptorList;
    protected final SpecialColumnHandler specialColumnHandler;

    protected AbstractSqlExecutor(JdbcOperationHolderAdapter jdbcOperationHolderAdapter, List<SqlPrinter> sqlPrinterList, List<SqlInterceptor> sqlInterceptorList, SpecialColumnHandler specialColumnHandler) {
        this.jdbcOperationHolderAdapter = jdbcOperationHolderAdapter;
        this.sqlPrinterList = sqlPrinterList;
        this.sqlInterceptorList = sqlInterceptorList;
        this.specialColumnHandler = specialColumnHandler;
    }

    /**
     * 适配一个jdbc操作对象
     */
    protected JdbcOperationHolder adapterJdbcTemplate(Class<? extends SqlType> type, Class<?> pojoType) {
        JdbcOperationHolder jdbcOperationHolder = JdbcOperationSupport.get();
        if (null == jdbcOperationHolder) {
            jdbcOperationHolder = jdbcOperationHolderAdapter.adapt(type, pojoType);
            JdbcOperationSupport.set(jdbcOperationHolder);
        }
        return jdbcOperationHolder;
    }

    /**
     * 打印sql
     */
    protected void printTo(SqlParameterMapping sqlParameterMapping) {
        for (SqlPrinter sqlPrinter : sqlPrinterList) {
            if (sqlPrinter.support(sqlParameterMapping)) {
                sqlPrinter.printTo(sqlParameterMapping);
            }
        }
    }

    /**
     * 拦截器
     */
    protected void preExecute(SqlParameterMapping sqlParameterMapping) {
        for (SqlInterceptor interceptor : sqlInterceptorList) {
            if (interceptor.intercept(sqlParameterMapping)) {
                interceptor.preExecute(sqlParameterMapping);
            }
        }
    }

    protected void process(SqlParameterMapping sqlParameterMapping) {
        // 打印
        this.printTo(sqlParameterMapping);
        // 处理特殊列
        this.processSpecialColumn(sqlParameterMapping);
        // 拦截器
        this.preExecute(sqlParameterMapping);
    }

    protected void processSpecialColumn(SqlParameterMapping sqlParameterMapping) {
        specialColumnHandler.handle(sqlParameterMapping.getParam(), sqlParameterMapping.getParameterMappings());
    }
}
