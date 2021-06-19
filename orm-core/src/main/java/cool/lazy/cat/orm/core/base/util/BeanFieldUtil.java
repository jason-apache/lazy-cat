package cool.lazy.cat.orm.core.base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

/**
 * @author: mahao
 * @date: 2021/4/14 09:51
 */
public final class BeanFieldUtil {

    private BeanFieldUtil() {}

    /**
     * 判断给定的属性名字符串是否以对象名开头
     *  eg：
     *      objName: office  fieldName: office.name  return true
     *      objName: office  fieldName: name  return false
     * @param objName 对象变量名称
     * @param fieldName 属性名
     * @return 是否以对象名开头
     */
    public static boolean isBelonged(String objName, String fieldName) {
        return null != objName && null != fieldName && fieldName.startsWith(objName) && fieldName.lastIndexOf(".") == objName.length();
    }

    /**
     * 获取类型的class对象
     * 如果是一个数组或者Collection，则尝试获取其中第一个元素的class
     * @param obj 数据
     * @return class
     */
    public static Class<?> getTypeFromObj(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).iterator().next().getClass();
        }
        if (obj.getClass().isArray()) {
            return ((Object[]) obj)[0].getClass();
        }
        return obj.getClass();
    }

    /**
     * 适配并转换，转换值为预期的类型，如果不能处理预期的类型，则抛出UnsupportedOperationException
     * @param targetType 预期的类型
     * @param value 值
     * @return 转换后的值
     */
    public static Object typeAdapter(Class<?> targetType, Object value) {
        if (null == value || value.getClass() == targetType) {
            return value;
        }
        if (targetType == String.class) {
            return value.toString();
        }
        if (targetType == BigInteger.class) {
            return BigInteger.valueOf(Long.parseLong(value.toString()));
        }
        if (targetType == BigDecimal.class) {
            return BigDecimal.valueOf(Long.parseLong(value.toString()));
        }
        if (long.class == targetType || targetType == Long.class) {
            return Long.parseLong(value.toString());
        }
        if (int.class == targetType || targetType == Integer.class) {
            return Integer.valueOf(value.toString());
        }
        if (double.class == targetType || targetType == Double.class) {
            return Double.valueOf(value.toString());
        }
        if (float.class == targetType || targetType == Float.class) {
            return Float.valueOf(value.toString());
        }
        if (boolean.class == targetType || targetType == Boolean.class) {
            return Boolean.valueOf(value.toString());
        }
        if (short.class == targetType || targetType == Short.class) {
            return Short.valueOf(value.toString());
        }
        if (byte.class == targetType || targetType == Byte.class) {
            return Byte.valueOf(value.toString());
        }
        if (char.class == targetType || targetType == Character.class) {
            return value.toString().isEmpty() ? null : value.toString().charAt(0);
        }
        if (targetType == Date.class) {
            return convertToDate(value);
        }
        if (targetType == java.sql.Date.class) {
            return convertToSqlDate(value);
        }
        if (targetType == LocalDate.class) {
            return convertToLocalDate(value);
        }
        if (targetType == LocalDateTime.class) {
            return convertToLocalDateTime(value);
        }
        if (targetType == LocalTime.class) {
            return convertToLocalTime(value);
        }
        if (targetType == Time.class) {
            return convertToTime(value);
        }
        if (targetType == Timestamp.class) {
            return convertToTimestamp(value);
        }
        throw error(targetType, value.getClass());
    }

    public static Date convertToDate(Object value) {
        if (value instanceof String) {
            return parseDate(value.toString());
        }
        if (value instanceof Long) {
            return Date.from(Instant.ofEpochMilli((Long) value));
        }
        if (value instanceof java.sql.Date) {
            return new Date(((java.sql.Date) value).getTime());
        }
        if (value instanceof Timestamp) {
            return new Date(((Timestamp) value).getTime());
        }
        if (value instanceof Time) {
            return new Date(((Time) value).getTime());
        }
        if (value instanceof LocalDate) {
            return Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (value instanceof LocalDateTime) {
            return Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
        }
        if (value instanceof LocalTime) {
            return Date.from(LocalDateTime.now().withHour(((LocalTime) value).getHour()).withMinute(((LocalTime) value).getMinute())
                    .withSecond(((LocalTime) value).getSecond()).withNano(((LocalTime) value).getNano()).atZone(ZoneId.systemDefault()).toInstant());
        }
        throw error(Date.class, value.getClass());
    }

    private static Date parseDate(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date convertToSqlDate(Object value) {
        if (value instanceof String) {
            return java.sql.Date.valueOf(value.toString());
        }
        if (value instanceof Long) {
            return new java.sql.Date((Long) value);
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }
        if (value instanceof LocalDate) {
            return java.sql.Date.valueOf(((LocalDate) value));
        }
        if (value instanceof LocalDateTime) {
            return java.sql.Date.valueOf(((LocalDateTime) value).toLocalDate());
        }
        throw error(java.sql.Date.class, value.getClass());
    }

    public static LocalDate convertToLocalDate(Object value) {
        if (value instanceof String) {
            return LocalDate.parse(value.toString());
        }
        if (value instanceof Long) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) value), ZoneId.systemDefault()).toLocalDate();
        }
        if (value instanceof Date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Date) value).getTime()), ZoneId.systemDefault()).toLocalDate();
        }
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).toLocalDate();
        }
        throw error(LocalDate.class, value.getClass());
    }

    public static LocalDateTime convertToLocalDateTime(Object value) {
        if (value instanceof String) {
            return LocalDateTime.parse(value.toString());
        }
        if (value instanceof Long) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) value), ZoneId.systemDefault());
        }
        if (value instanceof Date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Date) value).getTime()), ZoneId.systemDefault());
        }
        if (value instanceof LocalDate) {
            return ((LocalDate) value).atStartOfDay();
        }
        if (value instanceof LocalTime) {
            return LocalDateTime.now().withHour(((LocalTime) value).getHour()).withMinute(((LocalTime) value).getMinute())
                    .withSecond(((LocalTime) value).getSecond()).withNano(((LocalTime) value).getNano());
        }
        throw error(LocalDateTime.class, value.getClass());
    }

    public static LocalTime convertToLocalTime(Object value) {
        if (value instanceof String) {
            return LocalTime.parse(value.toString());
        }
        if (value instanceof Long) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) value), ZoneId.systemDefault()).toLocalTime();
        }
        if (value instanceof Date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Date) value).getTime()), ZoneId.systemDefault()).toLocalTime();
        }
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).toLocalTime();
        }
        throw error(LocalTime.class, value.getClass());
    }

    public static Time convertToTime(Object value) {
        if (value instanceof String) {
            return Time.valueOf(value.toString());
        }
        if (value instanceof Long) {
            return new Time((Long) value);
        }
        if (value instanceof Date) {
            return new Time(((Date) value).getTime());
        }
        if (value instanceof LocalTime) {
            return Time.valueOf((LocalTime) value);
        }
        if (value instanceof LocalDateTime) {
            return Time.valueOf(((LocalDateTime) value).toLocalTime());
        }
        throw error(Time.class, value.getClass());
    }

    public static Timestamp convertToTimestamp(Object value) {
        if (value instanceof String) {
            return Timestamp.valueOf(value.toString());
        }
        if (value instanceof Long) {
            return new Timestamp((Long) value);
        }
        if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        }
        if (value instanceof LocalDate) {
            return new Timestamp(((LocalDate) value).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
        if (value instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) value);
        }
        if (value instanceof LocalTime) {
            return Timestamp.valueOf(LocalDateTime.now().withHour(((LocalTime) value).getHour()).withMinute(((LocalTime) value).getMinute())
                    .withSecond(((LocalTime) value).getSecond()).withNano(((LocalTime) value).getNano()));
        }
        throw error(Timestamp.class, value.getClass());
    }

    private static RuntimeException error(Class<?> targetType, Class<?> instanceType) {
        return new UnsupportedOperationException("无法处理的类型转换：目标类：" + targetType.getName() + "，实例：" + instanceType.getName());
    }
}
