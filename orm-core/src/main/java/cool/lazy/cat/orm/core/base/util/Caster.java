package cool.lazy.cat.orm.core.base.util;

/**
 * @author: mahao
 * @date: 2021/4/12 20:48
 * 类型强转转换工具类
 */
public final class Caster {

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
