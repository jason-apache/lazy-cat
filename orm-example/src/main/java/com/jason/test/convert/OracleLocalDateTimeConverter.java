package com.jason.test.convert;


import com.lazy.cat.orm.core.jdbc.JdbcConstant;
import com.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import com.lazy.cat.orm.core.jdbc.dialect.Dialect;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author: mahao
 * @date: 2021/4/24 15:24
 */
public class OracleLocalDateTimeConverter implements TypeConverter {

    @Override
    public Object convertToDb(Object instance, Object value, TableFieldInfo fieldInfo) {
        if (value instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) value);
        }
        return value;
    }

    @Override
    public boolean match(Dialect dialect) {
        return dialect.match(JdbcConstant.DATABASE_TYPE_ORACLE);
    }
}
