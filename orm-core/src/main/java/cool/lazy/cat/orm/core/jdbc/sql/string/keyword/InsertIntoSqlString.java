package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.CompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.OperationObjectSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:10
 */
public interface InsertIntoSqlString extends KeywordSqlString, OperationObjectSqlString, CompoundSqlString<SqlString> {

    @Override
    default boolean paperJam() {
        return CompoundSqlString.super.paperJam();
    }

    @Override
    default Cause cause() {
        return CompoundSqlString.super.cause();
    }
}
