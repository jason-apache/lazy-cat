package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.exception.NoMatchedDialectFoundException;
import cool.lazy.cat.orm.core.jdbc.exception.RepeatedRegistrationDialectException;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import org.springframework.boot.jdbc.DatabaseDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/25 13:58
 */
public class DialectAdapterImpl implements DialectAdapter {

    protected final Map<DatabaseDriver, Dialect> dialects;

    public DialectAdapterImpl(List<Dialect> dialectList) {
        this.dialects = new HashMap<>();
        for (Dialect dialect : dialectList) {
            Dialect registered = dialects.get(dialect.getDataBaseDriver());
            if (null != registered) {
                throw new RepeatedRegistrationDialectException("重复的dialect注册: " + dialect.getDataBaseDriver().name() + "\t" + registered.getClass().getName() + " -> " + dialect.getClass().getName());
            }
            dialects.put(dialect.getDataBaseDriver(), dialect);
        }
    }

    @Override
    public Dialect adapt(DatabaseDriver databaseDriver) {
        Dialect dialect = dialects.get(databaseDriver);
        if (null == dialect) {
            throw new NoMatchedDialectFoundException("无法匹配的方言类型: " + databaseDriver.name() + " 请检查是否存在此方言支持的bean");
        }
        return dialect;
    }
}
