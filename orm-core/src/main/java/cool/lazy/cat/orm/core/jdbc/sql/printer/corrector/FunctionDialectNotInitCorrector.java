package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.FunctionDialectNotInitCause;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/7/27 11:26
 */
public class FunctionDialectNotInitCorrector implements Corrector {

    @Override
    public boolean support(Class<? extends SqlType> type, Cause cause) {
        return cause instanceof FunctionDialectNotInitCause;
    }

    @Override
    public void fix(Cause ref, SqlString sqlString, SqlParameterMapping sqlParameterMapping) {
        FunctionDialectNotInitCause cause = Caster.cast(ref);
        FunctionSqlString functionSqlString = cause.getSqlString();
        JdbcOperationHolder jdbcOperationHolder = JdbcOperationSupport.getAndCheck();
        // 处理函数
        jdbcOperationHolder.getDialect().handleFunctionSqlString(functionSqlString);
        functionSqlString.setInitialization(true);
    }
}
