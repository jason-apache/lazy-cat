package com.jason.test.convert;

import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/10 13:32
 */
public class BooleanTypeConverter implements TypeConverter {

    private static final String TRUE = "1";
    private static final String FALSE = "0";

    @Override
    public Object convertFromDb(ResultSet resultSet, int index, Class<?> javaType) throws SQLException {
        Object val = TypeConverter.super.convertFromDb(resultSet, index, javaType);
        if (val == null) {
            return null;
        }
        return TRUE.equals(val);
    }

    @Override
    public Object convertToDb(Object value, PojoField pojoField) {
        if (null == value) {
            return null;
        }
        if (TRUE.equals(value) || FALSE.equals(value)) {
            return value;
        }
        if (Objects.equals(true, value)) {
            return TRUE;
        }
        return FALSE;
    }
}
