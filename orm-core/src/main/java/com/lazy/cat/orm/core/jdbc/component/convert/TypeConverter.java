package com.lazy.cat.orm.core.jdbc.component.convert;


import com.lazy.cat.orm.core.jdbc.dialect.Dialect;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: mahao
 * @date: 2021/3/10 13:25
 */
public interface TypeConverter {

    /**
     * 从数据库中查询结果集映射为指定类型
     * @param instance 实例对象
     * @param resultSet 结果集
     * @param columnIndex 列
     * @param javaType 反射对象 类型
     * @return 转换后的值
     * @throws SQLException
     */
    default Object convertFromDb(Object instance, ResultSet resultSet, int columnIndex, Class<?> javaType) throws SQLException {
        return JdbcUtils.getResultSetValue(resultSet, columnIndex, javaType);
    }

    /**
     * 新增、修改持久化至数据库时转换为指定类型
     * @param instance 实例对象
     * @param value 字段值
     * @param fieldInfo 字段相关信息
     * @return 转换后的值
     */
    Object convertToDb(Object instance, Object value, TableFieldInfo fieldInfo);

    /**
     * 根据数据库方言判断当前转换器是否启用
     * @param dialect 数据库方言
     * @return 是否满足
     */
    boolean match(Dialect dialect);
}
