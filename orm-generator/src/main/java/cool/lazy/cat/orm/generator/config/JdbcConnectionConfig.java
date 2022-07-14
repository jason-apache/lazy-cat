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

    private JdbcConnectionConfig(String url, String username, String password, String schema) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.schema = schema;
        this.databaseType = DatabaseType.adaptFromJdbcUrl(url);
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

    public static JdbcConnectionConfigBuilder builder() {
        return new JdbcConnectionConfigBuilder();
    }

    public static class JdbcConnectionConfigBuilder {
        private String url;
        private String username;
        private String password;
        private String schema;

        public JdbcConnectionConfigBuilder url(String url) {
            this.url = url;
            return this;
        }

        public JdbcConnectionConfigBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JdbcConnectionConfigBuilder password(String password) {
            this.password = password;
            return this;
        }

        public JdbcConnectionConfigBuilder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public JdbcConnectionConfig build() {
            return new JdbcConnectionConfig(url, username, password, schema);
        }
    }
}
