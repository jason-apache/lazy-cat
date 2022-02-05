package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;

/**
 * @author: mahao
 * @date: 2021/9/23 20:08
 */
public class WhereSqlStringImpl<S extends ConditionSqlString> extends AbstractCompoundSqlString<S> implements WhereSqlString<S>, KeywordSqlString {

    private int totalCount;

    public WhereSqlStringImpl() {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().where());
    }

    @Override
    public int getConditionTotalCount() {
        return totalCount;
    }

    @Override
    public int incrementAndGetTotalCount() {
        return ++ totalCount;
    }
}
