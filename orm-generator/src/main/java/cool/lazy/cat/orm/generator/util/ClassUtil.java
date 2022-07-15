package cool.lazy.cat.orm.generator.util;

/**
 * @author : jason.ma
 * @date : 2022/7/15 13:51
 */
public class ClassUtil {

    private ClassUtil() {}

    /**
     * 从class完全限定名中截取class名
     * @param fullName 完全限定名
     * @return className
     */
    public static String getClassSimpleName(String fullName) {
        if (StringUtil.isBlank(fullName)) {
            return null;
        }
        int indexOf = fullName.lastIndexOf(".");
        if (indexOf == -1) {
            return fullName;
        }
        return fullName.substring(indexOf + 1);
    }

    /**
     * 从class完全限定名中截取class package
     * @param fullName 完全限定名
     * @return class package
     */
    public static String getClassPackageName(String fullName) {
        if (StringUtil.isBlank(fullName)) {
            return null;
        }
        int indexOf = fullName.lastIndexOf(".");
        if (indexOf == -1) {
            return fullName;
        }
        return fullName.substring(0, indexOf);
    }
}
