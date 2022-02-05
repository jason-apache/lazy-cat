package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.FieldSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/7/16 11:05
 * 表示数据库字段的sqlString
 */
public interface FieldSqlString extends SqlString {

    /**
     * @return 与其他字段逗号连接
     */
    @Override
    default SqlStringJoiner joiner() {
        return new FieldSqlStringJoiner();
    }
}
