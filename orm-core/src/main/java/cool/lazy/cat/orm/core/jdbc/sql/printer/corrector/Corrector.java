package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/7/13 17:43
 * sql修正器
 * 不应将太过复杂的逻辑交给修正器处理 否则将使得debug变得异常困难
 */
public interface Corrector {

    /**
     * @param type sql操作类型
     * @param cause 原因
     * @return 是否支持
     */
    boolean support(Class<? extends SqlType> type, Cause cause);

    /**
     * 解决该问题
     * @param cause 原因
     * @param sqlString sql句子
     * @param sqlParameterMapping sql参数映射
     */
    void fix(Cause cause, SqlString sqlString, SqlParameterMapping sqlParameterMapping);
}
