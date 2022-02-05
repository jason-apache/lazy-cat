package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 13:52
 */
public abstract class AbstractSqlStringJoiner implements SqlStringJoiner {

    protected final String prefix;
    protected final String suffix;
    protected static final String EMPTY_STR = "";
    protected static final String SPACE = " ";
    protected static final String COMMA = ",";

    protected AbstractSqlStringJoiner(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String linkToPre(SqlString preSql) {
        return prefix;
    }
}
