package cool.lazy.cat.orm.core.jdbc.sql.string.joiner;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 13:45
 * sqlString连接工具 控制与其它sqlString实例连接时的符号
 */
public interface SqlStringJoiner {

    /**
     * @param preSql 前方的sqlString实例
     * @return 间隔符
     */
    String linkToPre(SqlString preSql);

    /**
     * @param nextSql 后方的sqlString实例
     * @return 间隔符
     */
    String linkToNext(SqlString nextSql);
}
