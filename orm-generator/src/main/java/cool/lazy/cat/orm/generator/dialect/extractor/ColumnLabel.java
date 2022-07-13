package cool.lazy.cat.orm.generator.dialect.extractor;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:28
 */
class ColumnLabel {
    private final String name;
    private final int index;

    public ColumnLabel(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "{" + "\"name\":\"" + name + '\"' + ",\"index\":" + index + '}';
    }
}
