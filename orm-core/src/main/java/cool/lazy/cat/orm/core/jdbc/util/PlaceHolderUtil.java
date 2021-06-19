package cool.lazy.cat.orm.core.jdbc.util;


import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.JdbcConfig;

/**
 * @author: mahao
 * @date: 2021/4/12 20:55
 */
public final class PlaceHolderUtil {

    public static String getFromStr(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        if (str.length() > 3 && str.charAt(0) == '$' && str.charAt(1) == '{' && str.charAt(str.length() -1) == '}') {
            return str.substring(2, str.length() - 1);
        }
        return null;
    }

    public static String getDynamicSchema(String schema, JdbcConfig jdbcConfig) {
        String val = getFromStr(schema);
        String dynamicSchema = jdbcConfig.getDbSchema().get(val);
        return dynamicSchema == null ? schema : dynamicSchema;
    }
}
