package cool.lazy.cat.orm.generator.jdbc;

import cool.lazy.cat.orm.generator.config.JdbcConnectionConfig;
import cool.lazy.cat.orm.generator.exception.GetConnectionException;
import cool.lazy.cat.orm.generator.util.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 连接管理
 * @author : jason.ma
 * @date : 2022/7/11 18:55
 */
public class ConnectionManager {

    private static volatile Connection connection;

    public static void initConnection(JdbcConnectionConfig config) {
        if (null != connection) {
            System.out.println("数据源连接被重置...");
        }
        try {
            connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
            if (StringUtil.isNotBlank(config.getSchema())) {
                connection.setSchema(config.getSchema());
            }
        } catch (SQLException e) {
            throw new GetConnectionException("获取数据库连接异常", e);
        }
    }

    public static Connection getConnection() {
        if (null == connection) {
            throw new UnsupportedOperationException("意外的错误: 数据库连接未初始化");
        }
        return connection;
    }
}
