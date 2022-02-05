package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.DynamicNameNotInitCause;

/**
 * @author: mahao
 * @date: 2021/9/16 09:40
 * 动态名称sqlString
 */
public interface DynamicNameSqlString extends NameSqlString, InitializationRequiredSqlString {

    /**
     * @return 当前操作pojo类型
     */
    Class<?> getPojoType();

    /**
     * 设置操作对象所在的schema
     * @param schema schema
     */
    void setSchema(String schema);

    /**
     * @return 操作对象所在的schema
     */
    String getSchema();

    /**
     * 设置操作对象的完整名称
     * @param name 名称
     */
    void setName(String name);

    /**
     * @return 操作对象的完整名称
     */
    String getFullName();

    /**
     * @return 卡纸原因: 动态名称未初始化
     */
    @Override
    default Cause cause() {
        return new DynamicNameNotInitCause(this);
    }
}
