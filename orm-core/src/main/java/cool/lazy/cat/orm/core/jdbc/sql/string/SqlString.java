package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SpaceSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/7/16 10:31
 */
public interface SqlString {

    /**
     * @return sqlString连接工具 控制与其它sqlString实例连接时的符号
     */
    default SqlStringJoiner joiner() {
        return new SpaceSqlStringJoiner();
    }

    /**
     * sqlString实例应当是一个sql句子中的组成部分 组合并连接它们 应当得出一个直接可执行的sql句子
     * @return sql
     */
    @Override
    String toString();

    /**
     * 打印sql句子可能遇到问题而造成打印机卡纸
     * @return 是否卡纸
     */
    boolean paperJam();

    /**
     * @return 卡纸原因
     */
    Cause cause();

    /**
     * 改变sqlString字符大小写
     * @param charCase 字符大小写
     */
    void changeCase(Case charCase);
}
