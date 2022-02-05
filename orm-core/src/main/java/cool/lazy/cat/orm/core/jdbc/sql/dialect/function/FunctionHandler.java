package cool.lazy.cat.orm.core.jdbc.sql.dialect.function;

import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/27 12:49
 * 函数处理器
 */
public interface FunctionHandler {

    /**
     * @param databaseDriver 数据连接类型
     * @param functionName 函数名
     * @return 是否支持
     */
    boolean support(DatabaseDriver databaseDriver, String functionName);

    /**
     * 处理函数
     * @param functionSqlString 函数sqlString实例
     */
    void handle(FunctionSqlString functionSqlString);
}
