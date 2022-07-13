package cool.lazy.cat.orm.generator.constant;

import cool.lazy.cat.orm.generator.util.StringUtil;

/**
 * @author : jason.ma
 * @date : 2022/7/8 18:43
 */
public enum DatabaseType {

    MYSQL(":mysql:", ":cobar:"),
    ORACLE(":oracle:"),
    POSTGRE_SQL(":postgresql:"),
    UNKNOWN()
    ;

    private final String[] urlIdentifier;

    DatabaseType(String... urlIdentifier) {
        this.urlIdentifier = urlIdentifier;
    }

    public static DatabaseType adaptFromJdbcUrl(String jdbcUrl) {
        if (StringUtil.isBlank(jdbcUrl)) {
            return UNKNOWN;
        }
        for (DatabaseType value : values()) {
            for (String identifier : value.urlIdentifier) {
                if (jdbcUrl.contains(identifier)) {
                    return value;
                }
            }
        }
        return UNKNOWN;
    }
}
