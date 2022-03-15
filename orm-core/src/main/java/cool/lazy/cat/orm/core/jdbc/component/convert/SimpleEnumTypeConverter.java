package cool.lazy.cat.orm.core.jdbc.component.convert;

import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: mahao
 * @date: 2021/11/5 10:53
 * 枚举类型转换器
 */
public class SimpleEnumTypeConverter implements TypeConverter {

    @Override
    public Object convertFromDb(ResultSet resultSet, int columnIndex, Class<?> javaType) throws SQLException {
        String name = resultSet.getString(columnIndex);
        if (null == name) {
            return null;
        }
        return Enum.valueOf(Caster.cast(javaType), name);
    }

    @Override
    public Object convertToDb(Object value, PojoField pojoField) {
        if (null == value) {
            return null;
        }
        return ((Enum<?>) value).name();
    }
}
