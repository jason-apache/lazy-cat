package cool.lazy.cat.orm.generator.config;

/**
 * @author : jason.ma
 * @date : 2022/7/13 12:04
 */
public class CodeStyleConfig {

    private final String basePackage;
    private final NamingConfig namingConfig;
    /**
     * 缩进
     */
    private final String indent;
    /**
     * 换行符
     */
    private final String lineSeparator;
    private final String booleanGetterPrefix;

    private CodeStyleConfig(String basePackage, NamingConfig namingConfig, String indent, String lineSeparator, String booleanGetterPrefix) {
        this.basePackage = basePackage;
        this.namingConfig = namingConfig;
        this.indent = indent;
        this.lineSeparator = lineSeparator;
        this.booleanGetterPrefix = booleanGetterPrefix;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public NamingConfig getNamingConfig() {
        return namingConfig;
    }

    public String getIndent() {
        return indent;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public String getBooleanGetterPrefix() {
        return booleanGetterPrefix;
    }

    public static CodeStyleConfigBuilder builder() {
        return new CodeStyleConfigBuilder();
    }

    public static class CodeStyleConfigBuilder {
        private String basePackage;
        private NamingConfig namingConfig = NamingConfig.builder().build();
        private String indent;
        private String lineSeparator;
        private String booleanGetterPrefix;

        public CodeStyleConfigBuilder basePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        public CodeStyleConfigBuilder namingConfig(NamingConfig namingConfig) {
            this.namingConfig = namingConfig;
            return this;
        }

        public CodeStyleConfigBuilder indent(String indent) {
            this.indent = indent;
            return this;
        }

        public CodeStyleConfigBuilder lineSeparator(String lineSeparator) {
            this.lineSeparator = lineSeparator;
            return this;
        }

        public CodeStyleConfigBuilder booleanGetterPrefix(String booleanGetterPrefix) {
            this.booleanGetterPrefix = booleanGetterPrefix;
            return this;
        }

        public CodeStyleConfig build() {
            return new CodeStyleConfig(this.basePackage, this.namingConfig, this.indent, this.lineSeparator, booleanGetterPrefix);
        }
    }
}
