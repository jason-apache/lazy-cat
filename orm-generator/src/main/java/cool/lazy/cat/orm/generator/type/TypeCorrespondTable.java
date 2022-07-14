package cool.lazy.cat.orm.generator.type;

import cool.lazy.cat.orm.generator.info.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * jdbcType与java类型对照表
 * @see java.sql.Types
 * @author : jason.ma
 * @date : 2022/7/11 18:02
 */
public class TypeCorrespondTable {

    private static final Map<Integer, TypeInfo> CORRESPOND_TABLE = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeCorrespondTable.class);

    static {
        initDefaultSettings();
    }

    public static void initDefaultSettings() {
        registry(Types.BIT, Boolean.class);
        registry(Types.TINYINT, Integer.class);
        registry(Types.SMALLINT, Integer.class);
        registry(Types.INTEGER, Integer.class);
        registry(Types.BIGINT, Long.class);
        registry(Types.FLOAT, Float.class);
        registry(Types.REAL, Float.class);
        registry(Types.DOUBLE, Double.class);
        registry(Types.NUMERIC, Long.class);
        registry(Types.DECIMAL, BigDecimal.class);
        registry(Types.CHAR, String.class);
        registry(Types.VARCHAR, String.class);
        registry(Types.LONGVARCHAR, String.class);
        registry(Types.DATE, Date.class);
        registry(Types.TIME, Time.class);
        registry(Types.TIMESTAMP, Timestamp.class);
        registry(Types.BINARY, byte[].class);
        registry(Types.VARBINARY, String.class);
        registry(Types.LONGVARBINARY, String.class);
        registry(Types.NULL, Object.class);
        registry(Types.OTHER, Object.class);
        registry(Types.JAVA_OBJECT, Object.class);
        registry(Types.DISTINCT, Object.class);
        registry(Types.STRUCT, Object.class);
        registry(Types.ARRAY, Object.class);
        registry(Types.BLOB, byte[].class);
        registry(Types.CLOB, String.class);
        registry(Types.REF, Object.class);
        registry(Types.DATALINK, String.class);
        registry(Types.BOOLEAN, Boolean.class);
        registry(Types.ROWID, Object.class);
        registry(Types.NCHAR, String.class);
        registry(Types.NVARCHAR, String.class);
        registry(Types.LONGNVARCHAR, String.class);
        registry(Types.NCLOB, String.class);
        registry(Types.SQLXML, String.class);
        registry(Types.REF_CURSOR, Object.class);
        registry(Types.TIME_WITH_TIMEZONE, Date.class);
        registry(Types.TIMESTAMP_WITH_TIMEZONE, Date.class);
    }

    public static TypeInfo javaType(Integer jdbcType) {
        return CORRESPOND_TABLE.getOrDefault(jdbcType, new TypeInfo(Object.class));
    }

    public static void registry(Integer jdbcType, TypeInfo typeInfo) {
        TypeInfo before = CORRESPOND_TABLE.put(jdbcType, typeInfo);
        if (null != before) {
            LOGGER.debug("对照表映射被替换 jdbcType:{} -> before: {} now: {}", jdbcType, before, typeInfo);
        }
    }

    public static void registry(Integer jdbcType, Class<?> javaType) {
        registry(jdbcType, new TypeInfo(javaType));
    }
}
