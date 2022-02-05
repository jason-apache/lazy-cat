package cool.lazy.cat.orm.core.jdbc.util;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author: mahao
 * @date: 2021/7/25 15:22
 */
public final class SqlStringJoinerHelper {

    private SqlStringJoinerHelper() {}

    public static String join(Collection<SqlString> sqlStrings, Case charCase) {
        if (charCase == JdbcConstant.DEFAULT_CASE) {
            return join(sqlStrings);
        } else {
            return joinWithUppercase(sqlStrings);
        }
    }

    /**
     * 合并sqlStrings
     * @param sqlStrings sqlStrings
     * @return 合并结果
     */
    private static String join(Collection<SqlString> sqlStrings) {
        if (CollectionUtil.isEmpty(sqlStrings)) {
            return "";
        }
        LinkedList<SqlString> copy = new LinkedList<>(sqlStrings);
        StringBuilder sb = new StringBuilder();
        SqlString preTemp = null;
        while (copy.peek() != null) {
            SqlString sqlString = copy.poll();
            SqlString nextSql = copy.peek();
            SqlStringJoiner joiner = sqlString.joiner();
            sb.append(joiner.linkToPre(preTemp)).append(sqlString).append(joiner.linkToNext(nextSql));
            preTemp = sqlString;
        }
        return sb.toString();
    }

    /**
     * 合并sqlStrings 并转大写
     * @param sqlStrings sqlStrings
     * @return 合并结果
     */
    private static String joinWithUppercase(Collection<SqlString> sqlStrings) {
        if (CollectionUtil.isEmpty(sqlStrings)) {
            return "";
        }
        LinkedList<SqlString> copy = new LinkedList<>(sqlStrings);
        StringBuilder sb = new StringBuilder();
        SqlString preTemp = null;
        while (copy.peek() != null) {
            SqlString sqlString = copy.poll();
            sqlString.changeCase(Case.UPPERCASE);
            SqlString nextSql = copy.peek();
            SqlStringJoiner joiner = sqlString.joiner();
            if (null != preTemp) {
                sb.append(joiner.linkToPre(preTemp));
            }
            sb.append(sqlString).append(joiner.linkToNext(nextSql));
            preTemp = sqlString;
        }
        return sb.toString();
    }

}
