package cool.lazy.cat.orm.generator.config;

/**
 * 代码生成配置
 * @author : jason.ma
 * @date : 2022/7/12 16:20
 */
public class CodeGeneratorConfig {
    private JdbcConnectionConfig jdbcConnectionConfig;

    public CodeGeneratorConfig() {
    }

    public CodeGeneratorConfig(JdbcConnectionConfig jdbcConnectionConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
    }

    public JdbcConnectionConfig getJdbcConnectionConfig() {
        return jdbcConnectionConfig;
    }

    public void setJdbcConnectionConfig(JdbcConnectionConfig jdbcConnectionConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
    }
}
