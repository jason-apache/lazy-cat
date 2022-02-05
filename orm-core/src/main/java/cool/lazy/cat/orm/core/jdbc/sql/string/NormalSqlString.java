package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;

/**
 * @author: mahao
 * @date: 2021/7/28 16:34
 * 普通的sqlString实例
 */
public interface NormalSqlString extends SqlString {

    /**
     * @return 不会卡纸
     */
    @Override
    default boolean paperJam() {
        return false;
    }

    @Override
    default Cause cause() {
        return null;
    }
}
