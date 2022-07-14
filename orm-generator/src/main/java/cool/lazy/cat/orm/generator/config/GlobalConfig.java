package cool.lazy.cat.orm.generator.config;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:16
 */
public class GlobalConfig {

    private final JdbcConnectionConfig jdbcConnectionConfig;
    private final CodeGeneratorConfig codeGeneratorConfig;
    private final ScanningConfig scanningConfig;

    private GlobalConfig(JdbcConnectionConfig jdbcConnectionConfig, CodeGeneratorConfig codeGeneratorConfig, ScanningConfig scanningConfig) {
        this.jdbcConnectionConfig = jdbcConnectionConfig;
        this.codeGeneratorConfig = codeGeneratorConfig;
        this.scanningConfig = scanningConfig;
    }

    public CodeGeneratorConfig getCodeGeneratorConfig() {
        return codeGeneratorConfig;
    }

    public JdbcConnectionConfig getJdbcConnectionConfig() {
        return jdbcConnectionConfig;
    }

    public ScanningConfig getScanningConfig() {
        return scanningConfig;
    }

    public static GlobalConfigBuilder builder() {
        return new GlobalConfigBuilder();
    }

    public static class GlobalConfigBuilder {
        private JdbcConnectionConfig jdbcConnectionConfig;
        private CodeGeneratorConfig codeGeneratorConfig = CodeGeneratorConfig.builder().build();
        private ScanningConfig scanningConfig = ScanningConfig.builder().build();

        public GlobalConfigBuilder jdbcConnectionConfig(JdbcConnectionConfig jdbcConnectionConfig) {
            this.jdbcConnectionConfig = jdbcConnectionConfig;
            return this;
        }

        public GlobalConfigBuilder codeGeneratorConfig(CodeGeneratorConfig codeGeneratorConfig) {
            this.codeGeneratorConfig = codeGeneratorConfig;
            return this;
        }

        public GlobalConfigBuilder scanningConfig(ScanningConfig scanningConfig) {
            this.scanningConfig = scanningConfig;
            return this;
        }

        public GlobalConfig build() {
            return new GlobalConfig(jdbcConnectionConfig, codeGeneratorConfig, scanningConfig);
        }
    }
}
