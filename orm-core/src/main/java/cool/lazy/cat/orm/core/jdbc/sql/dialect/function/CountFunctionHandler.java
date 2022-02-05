package cool.lazy.cat.orm.core.jdbc.sql.dialect.function;

import cool.lazy.cat.orm.core.jdbc.exception.FunctionArgumentException;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/27 12:52
 */
public class CountFunctionHandler implements FunctionHandler {

    @Override
    public boolean support(DatabaseDriver databaseDriver, String functionName) {
        return "count".equalsIgnoreCase(functionName);
    }

    @Override
    public void handle(FunctionSqlString functionSqlString) {
        String functionName = functionSqlString.getFunctionName();
        Object[] args = functionSqlString.getArgs();
        if (null == args || args.length == 0) {
            throw new FunctionArgumentException("count函数参数不能为空!");
        }
        if (!(args instanceof String[])) {
            throw new FunctionArgumentException("count函数参数只能为字符串!");
        }
        functionSqlString.setPayload(functionName + "(" + String.join(",", (String[]) args) + ")");
    }
}
