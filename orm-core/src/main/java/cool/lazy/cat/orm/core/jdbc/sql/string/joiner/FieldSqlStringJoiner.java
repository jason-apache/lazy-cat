package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.sql.string.FieldSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 14:05
 */
public class FieldSqlStringJoiner extends AbstractSqlStringJoiner {

    public FieldSqlStringJoiner() {
        super(EMPTY_STR, COMMA + SPACE);
    }

    @Override
    public String linkToNext(SqlString nextSql) {
        if (null == nextSql) {
            return EMPTY_STR;
        }
        // 如果下一个sqlString实例是FieldSqlString实例, 则追加',' 否则追加空格
        if (!(nextSql instanceof FieldSqlString)) {
            return SPACE;
        }
        return suffix;
    }
}
