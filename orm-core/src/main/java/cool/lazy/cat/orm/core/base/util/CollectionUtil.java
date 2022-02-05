package cool.lazy.cat.orm.core.base.util;


import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/20 14:53
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return !isNotEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static boolean isEmpty(Object[] arr) {
        return !isNotEmpty(arr);
    }

    public static boolean isNotEmpty(Object[] arr) {
        return null != arr && arr.length > 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return !isNotEmpty(map);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return null != map && !map.isEmpty();
    }

    public static boolean contains(Object[] arr, Object target) {
        return null != arr && indexOf(arr, target) != -1;
    }

    public static int indexOf(Object[] arr, Object target) {
        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(target, arr[i])) {
                return i;
            }
        }
        return -1;
    }

    public static int sizeOf(Object obj) {
        if (null == obj) {
            return 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        }
        if (obj.getClass().isArray()) {
            return ((Object[]) obj).length;
        }
        return 1;
    }

    public static boolean containsAny(Collection<?> c1, Collection<?> c2) {
        if (null == c1 || null == c2) {
            return false;
        }
        for (Object o : c1) {
            if (c2.contains(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取类型的class对象
     * 如果是一个数组或者Collection，则尝试获取其中第一个元素的class
     * @param obj 数据
     * @return class
     */
    public static Class<?> getTypeFromObj(Object obj) {
        if (null == obj) {
            throw new IllegalArgumentException("非法参数: 空对象");
        }
        if (obj instanceof Collection) {
            if (((Collection<?>) obj).isEmpty()) {
                throw new IllegalArgumentException("非法参数: 空集合");
            }
            return ((Collection<?>) obj).iterator().next().getClass();
        }
        if (obj.getClass().isArray()) {
            if (((Object[]) obj).length == 0) {
                throw new IllegalArgumentException("非法参数: 空数组");
            }
            return ((Object[]) obj)[0].getClass();
        }
        return obj.getClass();
    }
}
