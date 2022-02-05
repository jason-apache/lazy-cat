package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 14:00
 * 紧凑的连接符
 */
public class CompactSqlStringJoiner extends AbstractSqlStringJoiner implements SqlStringJoiner {

    public CompactSqlStringJoiner() {
        super(EMPTY_STR, EMPTY_STR);
    }

    @Override
    public String linkToNext(SqlString nextSql) {
        return EMPTY_STR;
    }
}
