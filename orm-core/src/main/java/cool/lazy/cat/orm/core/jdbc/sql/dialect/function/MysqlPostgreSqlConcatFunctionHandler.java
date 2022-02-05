package cool.lazy.cat.orm.core.jdbc.sql.dialect.function;

import cool.lazy.cat.orm.core.jdbc.exception.FunctionArgumentException;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/27 14:14
 */
public class MysqlPostgreSqlConcatFunctionHandler implements FunctionHandler {

    @Override
    public boolean support(DatabaseDriver databaseDriver, String functionName) {
        return databaseDriver == DatabaseDriver.MYSQL || databaseDriver == DatabaseDriver.POSTGRESQL && "concat".equalsIgnoreCase(functionName);
    }

    @Override
    public void handle(FunctionSqlString functionSqlString) {
        String functionName = functionSqlString.getFunctionName();
        Object[] args = functionSqlString.getArgs();
        if (null == args || args.length <= 1) {
            throw new FunctionArgumentException("concat函数参数不能为空!且参数列表必须大于等于2");
        }
        if (!(args instanceof String[])) {
            throw new FunctionArgumentException("concat函数参数只能为字符串!");
        }
        functionSqlString.setPayload(functionName + "(" + String.join(",", (String[]) args) + ")");
    }
}
