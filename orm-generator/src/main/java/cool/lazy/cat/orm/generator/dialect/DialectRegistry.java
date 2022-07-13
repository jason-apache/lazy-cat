package cool.lazy.cat.orm.generator.dialect;

import cool.lazy.cat.orm.generator.constant.DatabaseType;
import cool.lazy.cat.orm.generator.exception.UnKnowDialectException;

import java.util.HashMap;

/**
 * @author : jason.ma
 * @date : 2022/7/12 17:32
 */
public class DialectRegistry extends HashMap<DatabaseType, Dialect> {
    private static final long serialVersionUID = 3041762896253608916L;

    private static final DialectRegistry INSTANCE = new DialectRegistry();

    public static DialectRegistry getInstance() {
        return INSTANCE;
    }

    private DialectRegistry() {
        super.put(DatabaseType.MYSQL, new MysqlDialect());
        // todo
    }

    public Dialect get(DatabaseType databaseType) {
        Dialect dialect = super.get(databaseType);
        if (null == dialect) {
            throw new UnKnowDialectException("未知数据库方言, 请自定义实现: " + databaseType);
        }
        return dialect;
    }
}
