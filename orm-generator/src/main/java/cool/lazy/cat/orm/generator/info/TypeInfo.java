package cool.lazy.cat.orm.generator.info;

/**
 * @author : jason.ma
 * @date : 2022/7/14 12:37
 */
public class TypeInfo {

    private final String name;
    private final String fullName;

    public TypeInfo(String name, String fullName) {
        this.name = name;
        this.fullName = fullName;
    }

    public TypeInfo(Class<?> type) {
        this(type.getSimpleName(), type.getName());
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "{" + "\"name\":\"" + name + '\"' + ",\"fullName\":\"" + fullName + '\"' + '}';
    }
}
