package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.CompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.FieldSqlString;

/**
 * @author: mahao
 * @date: 2021/10/11 13:21
 */
public interface OrderBySqlString extends KeywordSqlString, CompoundSqlString<FieldSqlString> {

    boolean isAsc();

    @Override
    default boolean paperJam() {
        return CompoundSqlString.super.paperJam();
    }

    @Override
    default Cause cause() {
        return CompoundSqlString.super.cause();
    }
}
