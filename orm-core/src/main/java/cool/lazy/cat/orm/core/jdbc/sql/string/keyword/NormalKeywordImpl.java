package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:09
 */
public class NormalKeywordImpl extends AbstractSqlString implements KeywordSqlString {

    public NormalKeywordImpl(String val) {
        super(val);
    }
}
