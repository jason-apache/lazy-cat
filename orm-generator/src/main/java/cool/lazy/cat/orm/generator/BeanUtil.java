package cool.lazy.cat.orm.generator;

import cool.lazy.cat.orm.generator.exception.FieldLoadConfigPropertiesException;
import cool.lazy.cat.orm.generator.exception.ReflectException;
import cool.lazy.cat.orm.generator.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author : jason.ma
 * @date : 2022/7/13 16:22
 */
public class BeanUtil {

    static final Properties PROPERTIES = new Properties();
    static final Map<Class<?>, Object> INSTANCE_CACHE = new HashMap<>();

    static {
        InputStream inputStream;
        ClassLoader cl = BeanUtil.class.getClassLoader();
        if (cl != null) {
            inputStream = cl.getResourceAsStream("properties.properties");
        } else {
            inputStream = ClassLoader.getSystemResourceAsStream("properties.properties");
        }
        try {
            PROPERTIES.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            // todo
            throw new FieldLoadConfigPropertiesException(e);
        }
    }

    public static String getProperties(String key) {
        String property = System.getProperty(key);
        if (StringUtil.isBlank(property)) {
            property = (String) PROPERTIES.get(key);
        }
        return property;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanInstance(Class<T> type) {
        return (T) INSTANCE_CACHE.computeIfAbsent(type, k -> {
            try {
                return Class.forName(getProperties(type.getSimpleName() + ".instance")).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new ReflectException("反射初始化Bean失败!", e);
            }
        });
    }
}
