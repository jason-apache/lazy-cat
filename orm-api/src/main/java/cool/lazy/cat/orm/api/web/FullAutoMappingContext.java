package cool.lazy.cat.orm.api.web;

/**
 * @author: mahao
 * @date: 2021/3/7 15:22
 */
public class FullAutoMappingContext {

    private final static ThreadLocal<Class<?>> POJO_CLASS = new ThreadLocal<>();

    public static Class<?> getPojoType() {
        return POJO_CLASS.get();
    }

    public static void setPojoType(Class<?> pojoType) {
        POJO_CLASS.set(pojoType);
    }

    public static void removePojoType() {
        POJO_CLASS.remove();
    }
}
