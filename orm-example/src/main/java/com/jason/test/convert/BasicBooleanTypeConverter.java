package com.jason.test.convert;

import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/10/5 18:54
 * 基本类型boolean 只有0和1
 */
public class BasicBooleanTypeConverter implements TypeConverter {

    private static final String TRUE = "1";
    private static final String FALSE = "0";

    @Override
    public Object convertFromDb(ResultSet resultSet, int index, Class<?> javaType) throws SQLException {
        Object val = resultSet.getString(index);
        return TRUE.equals(val);
    }

    @Override
    public Object convertToDb(Object value, PojoField pojoField) {
        if (TRUE.equals(value) || FALSE.equals(value)) {
            return value;
        }
        if (Objects.equals(true, value)) {
            return TRUE;
        }
        return FALSE;
    }
}
