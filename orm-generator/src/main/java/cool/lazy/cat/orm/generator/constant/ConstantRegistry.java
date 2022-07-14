package cool.lazy.cat.orm.generator.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:35
 */
public class ConstantRegistry {
    static final Map<ConstantEnum, Object> MAP = new HashMap<>();

    static {
        initDefaultSetting();
    }

    public static void initDefaultSetting() {
        MAP.put(ConstantEnum.INDENT, "    ");
        MAP.put(ConstantEnum.LINE_SEPARATOR, "\r\n");
        MAP.put(ConstantEnum.BOOLEAN_GETTER_NAMING_PREFIX, "is");
    }

    public static void set(ConstantEnum constant, Object val) {
        MAP.put(constant, val);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(ConstantEnum constant, Class<T> type) {
        return (T) MAP.get(constant);
    }

    public static String getString(ConstantEnum constant) {
        return (String) MAP.get(constant);
    }
}
