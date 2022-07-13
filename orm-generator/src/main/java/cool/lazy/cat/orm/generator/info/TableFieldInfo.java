package cool.lazy.cat.orm.generator.info;

/**
 * @author : jason.ma
 * @date : 2022/7/11 15:48
 */
public class TableFieldInfo {

    /**
     * 字段名称
     */
    private final String name;
    /**
     * 字段类型
     */
    private final Integer type;
    /**
     * 长度
     */
    private final Integer length;
    /**
     * 非空字段
     */
    private final boolean nullable;
    /**
     * 默认值
     */
    private final String defaultValue;
    /**
     * 数字长度
     */
    private final Integer scale;
    /**
     * 字段注释
     */
    private final String comment;
    /**
     * 是否关键字
     */
    private final boolean keyWord;
    /**
     * 是否为主键
     */
    private final boolean primaryKey;
    /**
     * 是否为自增类型字段
     */
    private final boolean autoIncrement;

    public TableFieldInfo(String name, Integer type, Integer length, boolean nullable, String defaultValue, Integer scale,
                          String comment, boolean keyWord, boolean primaryKey, boolean autoIncrement) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.scale = scale;
        this.comment = comment;
        this.keyWord = keyWord;
        this.primaryKey = primaryKey;
        this.autoIncrement = autoIncrement;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Integer getScale() {
        return scale;
    }

    public String getComment() {
        return comment;
    }

    public boolean isKeyWord() {
        return keyWord;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public String toString() {
        return "{" + "\"name\":\"" + name + '\"' + ",\"type\":" + type + ",\"comment\":\"" + comment + '\"' + '}';
    }

    public static TableFieldInfoBuilder builder() {
        return new TableFieldInfoBuilder();
    }

    public static final class TableFieldInfoBuilder {
        private String name;
        private Integer type;
        private Integer length;
        private boolean nullable;
        private String defaultValue;
        private Integer scale;
        private String comment;
        private boolean keyWord;
        private boolean primaryKey;
        private boolean autoIncrement;

        public TableFieldInfoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TableFieldInfoBuilder type(Integer type) {
            this.type = type;
            return this;
        }

        public TableFieldInfoBuilder length(Integer length) {
            this.length = length;
            return this;
        }

        public TableFieldInfoBuilder nullable(boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public TableFieldInfoBuilder defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public TableFieldInfoBuilder scale(Integer scale) {
            this.scale = scale;
            return this;
        }

        public TableFieldInfoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public TableFieldInfoBuilder keyWord(boolean keyWord) {
            this.keyWord = keyWord;
            return this;
        }

        public TableFieldInfoBuilder primaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public TableFieldInfoBuilder autoIncrement(boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
            return this;
        }

        public TableFieldInfo build() {
            return new TableFieldInfo(name, type, length, nullable, defaultValue, scale, comment, keyWord, primaryKey, autoIncrement);
        }
    }
}
