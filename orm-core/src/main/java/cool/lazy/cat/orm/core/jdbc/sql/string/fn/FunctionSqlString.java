package cool.lazy.cat.orm.core.jdbc.sql.string.fn;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.FunctionDialectNotInitCause;
import cool.lazy.cat.orm.core.jdbc.sql.string.InitializationRequiredSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;

/**
 * @author: mahao
 * @date: 2021/7/27 10:48
 * 表示调用数据库函数的sqlString实例
 */
public interface FunctionSqlString extends InitializationRequiredSqlString {

    /**
     * @return 函数名称 [sum, count, avg, concat, nvl, .......]
     */
    String getFunctionName();

    /**
     * 函数或许是需要参数的
     * @return 参数名称实例
     */
    ParameterNameSqlString getParameterName();

    /**
     * @return 参数内容载体
     */
    Object[] getArgs();

    /**
     * @param payload 负载(经过初始化后可以表示为String的内容)
     */
    void setPayload(String payload);

    /**
     * @return 卡纸原因: 函数未初始化
     */
    @Override
    default Cause cause() {
        return new FunctionDialectNotInitCause(this);
    }
}
