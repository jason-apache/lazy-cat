package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.exception.NoMatchedFunctionHandlerFoundException;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.FunctionHandler;
import org.springframework.boot.jdbc.DatabaseDriver;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/7/27 13:15
 */
public class FunctionHandlerAdapterImpl implements FunctionHandlerAdapter {

    protected final List<FunctionHandler> functionHandlerList;
    protected final Map<FunctionKey, FunctionHandler> cache;

    public FunctionHandlerAdapterImpl(List<FunctionHandler> functionHandlerList) {
        this.functionHandlerList = functionHandlerList;
        this.cache = new ConcurrentHashMap<>(functionHandlerList.size());
    }

    @Override
    public FunctionHandler adapt(DatabaseDriver databaseDriver, String functionName) {
        FunctionKey functionKey = new FunctionKey(databaseDriver, functionName);
        FunctionHandler h = cache.get(functionKey);
        if (null != h) {
            return h;
        }
        for (FunctionHandler handler : functionHandlerList) {
            if (handler.support(databaseDriver, functionName)) {
                cache.put(functionKey, handler);
                return handler;
            }
        }
        throw new NoMatchedFunctionHandlerFoundException("无法匹配functionHandler类型: " + databaseDriver.name() + ":" + functionName + ", 请检查是否存在此数据库类型的函数处理器");
    }

    private final static class FunctionKey {
        private final DatabaseDriver databaseDriver;
        private final String functionName;

        private FunctionKey(DatabaseDriver databaseDriver, String functionName) {
            this.databaseDriver = databaseDriver;
            this.functionName = functionName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FunctionKey mapKey = (FunctionKey) o;
            if (databaseDriver != mapKey.databaseDriver) {
                return false;
            }
            return Objects.equals(functionName, mapKey.functionName);
        }

        @Override
        public int hashCode() {
            int result = databaseDriver != null ? databaseDriver.hashCode() : 0;
            result = 31 * result + (functionName != null ? functionName.hashCode() : 0);
            return result;
        }
    }
}
