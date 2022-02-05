package cool.lazy.cat.orm.core.jdbc.sql.printer.cause;

import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlString;

/**
 * @author: mahao
 * @date: 2021/7/28 15:28
 * 动态名称未初始化
 */
public class DynamicNameNotInitCause implements NotInitCause, Cause {

    private final DynamicNameSqlString dynamicNameSqlString;

    public DynamicNameNotInitCause(DynamicNameSqlString dynamicNameSqlString) {
        this.dynamicNameSqlString = dynamicNameSqlString;
    }

    @Override
    public DynamicNameSqlString getSqlString() {
        return dynamicNameSqlString;
    }
}
