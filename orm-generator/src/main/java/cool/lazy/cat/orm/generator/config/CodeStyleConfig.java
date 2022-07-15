package cool.lazy.cat.orm.generator.config;

/**
 * @author : jason.ma
 * @date : 2022/7/13 12:04
 */
public class CodeStyleConfig {

    private final String basePackage;
    private final String[] classExtends;
    private final String[] classImplements;
    private final NamingConfig namingConfig;
    /**
     * 缩进
     */
    private final String indent;
    /**
     * 换行符
     */
    private final String lineSeparator;
    /**
     * java代码元素之间的间隔符
     */
    private final String codeSeparator;
    private final String booleanGetterPrefix;

    public CodeStyleConfig(String basePackage, String[] classExtends, String[] classImplements, NamingConfig namingConfig, String indent, String lineSeparator, String codeSeparator, String booleanGetterPrefix) {
        this.basePackage = basePackage;
        this.classExtends = classExtends;
        this.classImplements = classImplements;
        this.namingConfig = namingConfig;
        this.indent = indent;
        this.lineSeparator = lineSeparator;
        this.codeSeparator = codeSeparator;
        this.booleanGetterPrefix = booleanGetterPrefix;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String[] getClassExtends() {
        return classExtends;
    }

    public String[] getClassImplements() {
        return classImplements;
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

    public String getCodeSeparator() {
        return codeSeparator;
    }

    public String getBooleanGetterPrefix() {
        return booleanGetterPrefix;
    }

    public static CodeStyleConfigBuilder builder() {
        return new CodeStyleConfigBuilder();
    }

    public static class CodeStyleConfigBuilder {
        private String basePackage;
        private String[] classExtends;
        private String[] classImplements;
        private NamingConfig namingConfig = NamingConfig.builder().build();
        private String indent;
        private String lineSeparator;
        private String codeSeparator;
        private String booleanGetterPrefix;

        public CodeStyleConfigBuilder basePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        public CodeStyleConfigBuilder classExtends(String... classExtends) {
            this.classExtends = classExtends;
            return this;
        }

        public CodeStyleConfigBuilder classImplements(String... classImplements) {
            this.classImplements = classImplements;
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

        public CodeStyleConfigBuilder codeSeparator(String codeSeparator) {
            this.codeSeparator = codeSeparator;
            return this;
        }

        public CodeStyleConfigBuilder booleanGetterPrefix(String booleanGetterPrefix) {
            this.booleanGetterPrefix = booleanGetterPrefix;
            return this;
        }

        public CodeStyleConfig build() {
            return new CodeStyleConfig(this.basePackage, this.classExtends, this.classImplements, this.namingConfig, this.indent, this.lineSeparator, this.codeSeparator, booleanGetterPrefix);
        }
    }
}
