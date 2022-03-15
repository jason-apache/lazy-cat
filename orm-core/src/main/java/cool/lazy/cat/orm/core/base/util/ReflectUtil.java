package cool.lazy.cat.orm.core.base.util;


import cool.lazy.cat.orm.core.base.exception.ReflectiveException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author: mahao
 * @date: 2021/4/1 13:26
 */
public final class ReflectUtil {

    public static Object newInstance(Class<?> type) {
        try {
            return type.newInstance();
        } catch (IllegalAccessException|InstantiationException e) {
            throw new ReflectiveException("反射异常: 无法初始化实例", e);
        }
    }

    public static Object invokeGetter(Method method, Object instance) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectiveException("反射异常: Getter方法", e);
        }
    }

    public static void invokeSetter(Method method, Object instance, Object val) {
        try {
            method.invoke(instance, val);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectiveException("反射异常: Setter方法", e);
        }
    }

    public static boolean canInstantiate(Class<?> type) {
        return null != type && !Modifier.isAbstract(type.getModifiers()) && !type.isEnum();
    }
}
