package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/9 13:22
 * 复合sqlString实例
 * 允许包含SqlString实例集 它们应当是一个整体
 */
public interface CompoundSqlString<S extends SqlString> extends SqlString, SwitchSymbolSqlString {

    /**
     * 添加一个实例
     * @param s SqlString实例
     */
    void combination(S s);

    /**
     * @return 内部实例集
     */
    Collection<S> getContent();

    /**
     * 实例集应当是一个整体 判断卡纸时也应逐个判断
     * @return 是否卡纸
     */
    @Override
    default boolean paperJam() {
        if (null != getContent()) {
            for (S s : getContent()) {
                if (s.paperJam()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 卡纸原因
     */
    @Override
    default Cause cause() {
        if (null != getContent()) {
            for (S s : getContent()) {
                if (s.paperJam()) {
                    return s.cause();
                }
            }
        }
        return null;
    }
}
