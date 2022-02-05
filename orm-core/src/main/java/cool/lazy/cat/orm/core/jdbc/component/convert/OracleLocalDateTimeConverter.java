package cool.lazy.cat.orm.core.jdbc.component.convert;


import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import org.springframework.boot.jdbc.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author: mahao
 * @date: 2021/4/24 15:24
 */
public class OracleLocalDateTimeConverter implements TypeConverter {

    @Override
    public Object convertToDb(Object value, PojoField pojoField) {
        if (null == value) {
            return null;
        }
        if (value instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) value);
        }
        return value;
    }

    @Override
    public Object convertFromDb(ResultSet resultSet, int columnIndex, Class<?> javaType) throws SQLException {
        Object val = TypeConverter.super.convertFromDb(resultSet, columnIndex, javaType);
        if (val instanceof Timestamp) {
            return ((Timestamp) val).toLocalDateTime();
        }
        return val;
    }

    @Override
    public boolean matchDialect(Dialect dialect) {
        return dialect.getDataBaseDriver() == DatabaseDriver.ORACLE;
    }
}
