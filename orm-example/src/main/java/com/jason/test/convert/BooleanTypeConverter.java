package com.jason.test.convert;

import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/10 13:32
 */
public class BooleanTypeConverter implements TypeConverter {

    @Override
    public Object convertFromDb(Object instance, ResultSet resultSet, int index, Class<?> javaType) throws SQLException {
        return "1".equals(resultSet.getString(index));
    }

    @Override
    public Object convertToDb(Object instance, Object value, TableFieldInfo fieldInfo) {
        if (Objects.equals(true, value)) {
            return "1";
        }
        return "0";
    }

    @Override
    public boolean match(Dialect dialect) {
        return true;
    }
}
