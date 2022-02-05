package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;

/**
 * @author: mahao
 * @date: 2021/7/13 17:25
 * sql打印机
 */
public interface SqlPrinter {

    /**
     * @param sqlParameterMapping sql参数映射
     * @return 是否满足打印条件
     */
    boolean support(SqlParameterMapping sqlParameterMapping);

    /**
     * 向sql参数映射中打印sql并注入参数
     * @param sqlParameterMapping sql参数映射
     */
    void printTo(SqlParameterMapping sqlParameterMapping);
}
