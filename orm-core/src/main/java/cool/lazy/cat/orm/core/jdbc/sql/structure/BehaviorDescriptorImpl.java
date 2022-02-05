package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.KeywordSqlString;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/13 15:51
 */
public class BehaviorDescriptorImpl implements BehaviorDescriptor {

    private final KeywordSqlString keyword;
    private final Collection<SqlString> content;

    public BehaviorDescriptorImpl(KeywordSqlString keyword, Collection<SqlString> content) {
        this.keyword = keyword;
        this.content = content;
    }

    @Override
    public KeywordSqlString getKeyword() {
        return keyword;
    }

    @Override
    public Collection<SqlString> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "BehaviorDescriptorImpl{" +
                "keyword=" + keyword +
                '}';
    }
}
