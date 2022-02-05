package cool.lazy.cat.orm.core.jdbc.component.convert;


import cool.lazy.cat.orm.core.jdbc.component.SpecialColumn;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: mahao
 * @date: 2021/3/10 13:25
 */
public interface TypeConverter extends SpecialColumn {

    /**
     * 从数据库中查询结果集映射为指定类型
     * @param resultSet 结果集
     * @param columnIndex 列
     * @param javaType 反射对象 类型
     * @return 转换后的值
     */
    default Object convertFromDb(ResultSet resultSet, int columnIndex, Class<?> javaType) throws SQLException {
        return JdbcUtils.getResultSetValue(resultSet, columnIndex, javaType);
    }

    /**
     * 新增、修改持久化至数据库时转换为指定类型
     * @param value pojo对象
     * @param pojoField 字段相关信息
     * @return 转换后的值
     */
    Object convertToDb(Object value, PojoField pojoField);

    @Override
    default void process(SqlSource sqlSource, PojoField pojoField) {
        sqlSource.set(pojoField.getJavaFieldName(), this.convertToDb(sqlSource.getValue(pojoField.getJavaFieldName()), pojoField));
    }

    /**
     * 根据数据库方言判断当前转换器是否启用
     * @param dialect 数据库方言
     * @return 是否满足
     */
    default boolean matchDialect(Dialect dialect) {
        return true;
    }
}
