package cool.lazy.cat.orm.generator.config;

/**
 * 代码生成配置
 * @author : jason.ma
 * @date : 2022/7/12 16:20
 */
public class CodeGeneratorConfig {
    private JdbcConnectionConfig jdbcConnectionConfig;

    private ScanningConfig scanningConfig = new ScanningConfig();

    public CodeGeneratorConfig(JdbcConnectionConfig jdbcConnectionConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
    }

    public CodeGeneratorConfig(JdbcConnectionConfig jdbcConnectionConfig, ScanningConfig scanningConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
        this.scanningConfig = scanningConfig;
    }

    public JdbcConnectionConfig getJdbcConnectionConfig() {
        return jdbcConnectionConfig;
    }

    public void setJdbcConnectionConfig(JdbcConnectionConfig jdbcConnectionConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
    }

    public ScanningConfig getScanningConfig() {
        return scanningConfig;
    }

    public void setScanningConfig(ScanningConfig scanningConfig) {
        this.scanningConfig = scanningConfig;
    }
}
