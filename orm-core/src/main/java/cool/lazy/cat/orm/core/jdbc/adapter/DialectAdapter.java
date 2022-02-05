package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/7/25 13:57
 * 方言适配器
 */
public interface DialectAdapter {

    /**
     * 根据数据库类型适配对应的方言
     * @param databaseDriver 数据库类型
     * @return 数据库方言
     */
    Dialect adapt(DatabaseDriver databaseDriver);
}
