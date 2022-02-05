package cool.lazy.cat.orm.core.jdbc.sql.dialect.function;

import cool.lazy.cat.orm.core.jdbc.exception.FunctionArgumentException;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/27 14:28
 */
public class OracleConcatFunctionHandler implements FunctionHandler {

    @Override
    public boolean support(DatabaseDriver databaseDriver, String functionName) {
        return databaseDriver == DatabaseDriver.ORACLE && "concat".equalsIgnoreCase(functionName);
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
        String payload = this.format(functionName, args[0], args[1]);
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                payload = this.format(functionName, payload, args[i]);
            }
        }
        functionSqlString.setPayload(payload);
    }

    private String format(String functionName, Object v1, Object v2) {
        return functionName + "(" + v1 + "," + v2 + ")";
    }
}
