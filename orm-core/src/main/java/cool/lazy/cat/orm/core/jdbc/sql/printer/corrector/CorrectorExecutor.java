package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/29 11:21
 * sql修正器执行器
 */
public interface CorrectorExecutor {

    /**
     * 修正sql句子存在的问题
     * @param sqlString sql句子
     * @param sqlParameterMapping sql参数映射
     */
    void correcting(SqlString sqlString, SqlParameterMapping sqlParameterMapping);
}
