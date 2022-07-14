package cool.lazy.cat.orm.generator.config;

/**
 * @author : jason.ma
 * @date : 2022/7/13 12:04
 */
public class NamingConfig {

    private final String classPrefix;
    private final String classSuffix;
    private final boolean enableSchema;
    private final boolean forceSchema;
    private final NamingStyle namingStyle;

    private NamingConfig(String classPrefix, String classSuffix, boolean enableSchema, boolean forceSchema, NamingStyle namingStyle) {
        this.classPrefix = classPrefix;
        this.classSuffix = classSuffix;
        this.enableSchema = enableSchema;
        this.forceSchema = forceSchema;
        this.namingStyle = namingStyle;
    }

    public String getClassPrefix() {
        return classPrefix;
    }

    public String getClassSuffix() {
        return classSuffix;
    }

    public boolean isEnableSchema() {
        return enableSchema;
    }

    public boolean isForceSchema() {
        return forceSchema;
    }

    public NamingStyle getNamingStyle() {
        return namingStyle;
    }

    public static NamingConfigBuilder builder() {
        return new NamingConfigBuilder();
    }

    public static class NamingConfigBuilder {

        private String classPrefix;
        private String classSuffix;
        private boolean enableSchema = true;
        private boolean forceSchema = true;
        private NamingStyle namingStyle = NamingStyle.UNDERLINE_2_CAMEL;

        public NamingConfigBuilder classPrefix(String classPrefix) {
            this.classPrefix = classPrefix;
            return this;
        }

        public NamingConfigBuilder classSuffix(String classSuffix) {
            this.classSuffix = classSuffix;
            return this;
        }

        public NamingConfigBuilder enableSchema(boolean enableSchema) {
            this.enableSchema = enableSchema;
            return this;
        }

        public NamingConfigBuilder forceSchema(boolean forceSchema) {
            this.forceSchema = forceSchema;
            return this;
        }

        public NamingConfigBuilder namingStyle(NamingStyle namingStyle) {
            this.namingStyle = namingStyle;
            return this;
        }

        public NamingConfig build() {
            return new NamingConfig(classPrefix, classSuffix, enableSchema, forceSchema, namingStyle);
        }
    }

    public enum NamingStyle {
        UNDERLINE_2_CAMEL, ORIGINAL
    }
}
