package cool.lazy.cat.orm.core.base.util;


import cool.lazy.cat.orm.core.base.exception.ReflectiveException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/4/1 13:26
 */
public final class InvokeHelper {

    public static Object invokeGetter(Method method, Object instance) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ReflectiveException("反射异常");
        }
    }

    public static void invokeSetter(Method method, Object instance, Object val) {
        try {
            method.invoke(instance, val);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ReflectiveException("反射异常");
        }
    }
}
