package cool.lazy.cat.orm.core.jdbc.sql.printer.cause;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/13 17:43
 */
public interface Cause {

    /**
     * @return 有问题的sql
     */
    SqlString getSqlString();
}
