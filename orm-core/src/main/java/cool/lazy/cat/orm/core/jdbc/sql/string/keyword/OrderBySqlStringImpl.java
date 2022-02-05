package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.FieldSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.PojoFieldSqlString;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/10/11 13:21
 */
public class OrderBySqlStringImpl extends AbstractCompoundSqlString<FieldSqlString> implements OrderBySqlString {

    private final boolean asc;

    public OrderBySqlStringImpl(boolean asc, List<String> fields) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().order() + " " + JdbcOperationSupport.getDialect().getKeywordDictionary().by());
        this.asc = asc;
        if (null != fields) {
            for (String field : fields) {
                super.combination(new PojoFieldSqlString(field, false));
            }
        }
    }

    @Override
    public boolean isAsc() {
        return asc;
    }

    @Override
    public String toString() {
        KeywordDictionary keywordDictionary = JdbcOperationSupport.getDialect().getKeywordDictionary();
        return super.toString() + " " + (this.asc ? keywordDictionary.asc() : keywordDictionary.desc());
    }
}
