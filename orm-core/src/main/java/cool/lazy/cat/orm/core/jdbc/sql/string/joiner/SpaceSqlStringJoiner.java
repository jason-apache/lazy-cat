package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 14:00
 * 空格连接符
 */
public class SpaceSqlStringJoiner extends AbstractSqlStringJoiner implements SqlStringJoiner {

    public SpaceSqlStringJoiner() {
        super(EMPTY_STR, SPACE);
    }

    @Override
    public String linkToNext(SqlString nextSql) {
        if (null == nextSql) {
            return EMPTY_STR;
        }
        return suffix;
    }
}
