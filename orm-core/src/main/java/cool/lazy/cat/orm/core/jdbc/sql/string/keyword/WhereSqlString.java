package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.CompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;

/**
 * @author: mahao
 * @date: 2021/9/23 20:11
 */
public interface WhereSqlString<S extends ConditionSqlString> extends KeywordSqlString, CompoundSqlString<S> {

    default void addConditionSql(S s) {
        this.combination(s);
    }

    /**
     * @return 包含的条件总数
     */
    int getConditionTotalCount();

    /**
     * 递增 并返回条件总数
     * @return 当前条件总数
     */
    int incrementAndGetTotalCount();

    @Override
    default boolean paperJam() {
        return CompoundSqlString.super.paperJam();
    }

    @Override
    default Cause cause() {
        return CompoundSqlString.super.cause();
    }
}
