package cool.lazy.cat.orm.generator.config;

/**
 * 代码生成配置
 * @author : jason.ma
 * @date : 2022/7/12 16:20
 */
public class CodeGeneratorConfig {

    private final CodeStyleConfig codeStyleConfig;
    private final FileOutputConfig fileOutputConfig;

    private CodeGeneratorConfig(CodeStyleConfig codeStyleConfig, FileOutputConfig fileOutputConfig) {
        this.codeStyleConfig = codeStyleConfig;
        this.fileOutputConfig = fileOutputConfig;
    }

    public CodeStyleConfig getCodeStyleConfig() {
        return codeStyleConfig;
    }

    public FileOutputConfig getFileOutputConfig() {
        return fileOutputConfig;
    }

    public static CodeGeneratorConfigBuilder builder() {
        return new CodeGeneratorConfigBuilder();
    }

    public static class CodeGeneratorConfigBuilder {
        private CodeStyleConfig codeStyleConfig = CodeStyleConfig.builder().build();
        private FileOutputConfig fileOutputConfig = FileOutputConfig.builder().build();

        public CodeGeneratorConfigBuilder codeStyleConfig(CodeStyleConfig codeStyleConfig) {
            this.codeStyleConfig = codeStyleConfig;
            return this;
        }

        public CodeGeneratorConfigBuilder fileOutputConfig(FileOutputConfig fileOutputConfig) {
            this.fileOutputConfig = fileOutputConfig;
            return this;
        }

        public CodeGeneratorConfig build() {
            return new CodeGeneratorConfig(codeStyleConfig, fileOutputConfig);
        }
    }
}
