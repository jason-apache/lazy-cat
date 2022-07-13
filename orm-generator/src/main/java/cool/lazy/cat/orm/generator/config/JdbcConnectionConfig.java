package cool.lazy.cat.orm.generator.config;

import cool.lazy.cat.orm.generator.constant.DatabaseType;
import cool.lazy.cat.orm.generator.jdbc.ConnectionManager;

import java.sql.Connection;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:58
 */
public class JdbcConnectionConfig {

    private final String url;
    private final String username;
    private final String password;
    private final String schema;
    private final DatabaseType databaseType;

    public JdbcConnectionConfig(String url, String username, String password, String schema) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.schema = schema;
        this.databaseType = DatabaseType.adaptFromJdbcUrl(url);
        ConnectionManager.initConnection(this);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
    public Connection getConnection() {
        return ConnectionManager.getConnection();
    }
}
