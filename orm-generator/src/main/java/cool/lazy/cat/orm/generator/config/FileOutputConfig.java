package cool.lazy.cat.orm.generator.config;

import java.nio.charset.Charset;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:22
 */
public class FileOutputConfig {

    private final String outputDirectory;
    private final Charset charset;

    private FileOutputConfig(String outputDirectory, Charset charset) {
        this.outputDirectory = outputDirectory;
        this.charset = charset;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public Charset getCharset() {
        return charset;
    }

    public static FileOutputConfigBuilder builder() {
        return new FileOutputConfigBuilder();
    }

    public static class FileOutputConfigBuilder {
        private String outputDirectory;
        private Charset charset;

        public FileOutputConfigBuilder outputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
            return this;
        }

        public FileOutputConfigBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public FileOutputConfig build() {
            return new FileOutputConfig(outputDirectory, charset);
        }
    }
}
