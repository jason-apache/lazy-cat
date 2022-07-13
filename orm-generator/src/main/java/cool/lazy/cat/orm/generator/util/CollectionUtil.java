package cool.lazy.cat.orm.generator.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author : jason.ma
 * @date : 2022/7/12 19:40
 */
public final class CollectionUtil {

    private CollectionUtil() {}

    public static boolean isNotEmpty(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return !isNotEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return !isNotEmpty(map);
    }
}
