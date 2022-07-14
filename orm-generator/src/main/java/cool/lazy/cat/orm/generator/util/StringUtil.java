package cool.lazy.cat.orm.generator.util;


import cool.lazy.cat.orm.generator.constant.Case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mahao
 * @date 2021/4/20 17:11
 */
public final class StringUtil {

    public static final String EMPTY = "";
    private static final Pattern CAMEL_REG = Pattern.compile("([A-Za-z\\d]+)(_)?");
    private static final Pattern UNDER_LINE_REG = Pattern.compile("[A-Z]([a-z\\d]+)?");

    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isBlank(CharSequence charSequence) {
        return null == charSequence || charSequence.toString().trim().length() == 0;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    /**
     * 将以下划线区分单词的字符串转换为小驼峰命名规范的字符串
     * @param str 字符串
     * @return 小驼峰命名规范的字符串
     */
    public static String underline2Camel(String str, boolean lowercase) {
        if (isBlank(str)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        Matcher matcher = CAMEL_REG.matcher(str);
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或是空的情况
            if (lowercase && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 将驼峰规范字符串转换为下划线
     * @param str 字符串
     * @return 以下划线区分单词的字符串
     */
    public static String camel2Underline(String str, Case charCase) {
        if (str == null || "".equals(str)) {
            return "";
        }
        str = String.valueOf(str.charAt(0)).toUpperCase().concat(str.substring(1));
        StringBuilder sb = new StringBuilder();
        Matcher matcher = UNDER_LINE_REG.matcher(str);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(charCase == Case.UPPERCASE ? word.toUpperCase() : word.toLowerCase());
            sb.append(matcher.end() == str.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static String lowerToUpperFirst(String word) {
        return String.valueOf(word.charAt(0)).toUpperCase().concat(word.substring(1));
    }
}
