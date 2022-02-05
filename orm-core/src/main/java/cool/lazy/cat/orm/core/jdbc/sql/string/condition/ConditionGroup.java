package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.CompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/12 19:52
 * ConditionGroup只作为一组条件的容器 对ConditionSqlString的接口方法都为空实现
 */
public interface ConditionGroup extends SqlString, ConditionSqlString, CompoundSqlString<ConditionSqlString> {

    @Override
    default boolean paperJam() {
        return CompoundSqlString.super.paperJam();
    }

    @Override
    default Cause cause() {
        return CompoundSqlString.super.cause();
    }

    @Override
    default boolean initialized() {
        return false;
    }

    @Override
    default void setInitialization(boolean initialization) {
    }

    @Override
    default String getParameterName() {
        return null;
    }

    @Override
    default Object getPayload() {
        return null;
    }

    @Override
    default ConditionType getConditionType() {
        return null;
    }
}
