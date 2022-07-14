package cool.lazy.cat.orm.generator.info;

/**
 * @author : jason.ma
 * @date : 2022/7/14 15:18
 */
public class MethodArgumentInfo {
    private final TypeInfo typeInfo;
    private final String argName;

    public MethodArgumentInfo(TypeInfo typeInfo, String argName) {
        this.typeInfo = typeInfo;
        this.argName = argName;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public String getArgName() {
        return argName;
    }
}
