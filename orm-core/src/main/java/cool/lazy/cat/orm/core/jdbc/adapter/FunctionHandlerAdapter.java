package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.FunctionHandler;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/27 13:14
 * 函数处理器适配器
 */
public interface FunctionHandlerAdapter {

    /**
     * 适配对应数据库类型的函数处理器
     * @param databaseDriver 数据库类型
     * @param functionName 函数名
     * @return 函数处理器
     */
    FunctionHandler adapt(DatabaseDriver databaseDriver, String functionName);
}
