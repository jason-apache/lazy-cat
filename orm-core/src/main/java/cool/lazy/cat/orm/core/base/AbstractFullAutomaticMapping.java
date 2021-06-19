package cool.lazy.cat.orm.core.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: mahao
 * @date: 2021/3/6 11:28
 */
public abstract class AbstractFullAutomaticMapping<P> implements FullAutomaticMapping {

    @Override
    public Class<?> getPojoType() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) this.getClass().getGenericSuperclass();
            Object clazz = p.getActualTypeArguments()[0];
            if (clazz instanceof Class) {
                return (Class<?>) p.getActualTypeArguments()[0];
            }
        }
        return null;
    }
}
