package com.lazy.cat.orm.core.jdbc.dialect;

import com.lazy.cat.orm.core.jdbc.dialect.type.DatabaseType;
import com.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 19:18
 * 数据库方言
 */
public interface Dialect {

    /**
     * 生成分页sql语句
     * @param searchParam 查询参数
     * @param originalSql 原始查询sql
     * @param indexes 列映射关系
     * @return 分页sql语句
     */
    String limitSql(SearchParam searchParam, StringBuilder originalSql, List<TableFieldInfoIndexWrapper> indexes);

    /**
     * 生成count查询语句
     * @param searchParam 查询参数
     * @param originalSql 原始查询sql
     * @return count查询语句
     */
    String countSql(SearchParam searchParam, StringBuilder originalSql);

    /**
     * 生成查询序列当前值的sql
     * @param schema 序列所在的库
     * @param sequenceName 序列名称
     * @return 查询序列当前值的sql
     */
    String selectSequenceCurrentValueSql(String schema, String sequenceName);

    /**
     * 生成查询序列下一个值的sql
     * @param schema 序列所在的库
     * @param sequenceName 序列名称
     * @return 查询序列下一个值的sql
     */
    String selectSequenceNextValueSql(String schema, String sequenceName);

    /**
     * 生成设置序列当前值的sql
     * @param schema 序列所在的库
     * @param sequenceName 序列名称
     * @return 设置序列当前值的sql
     */
    String setSequenceValueSql(String schema, String sequenceName, Object val);

    /**
     * 方言是否满足触发条件
     * @param databaseType 数据库类型
     * @param args 参数
     * @return 是否满足触发条件
     */
    default boolean match(DatabaseType databaseType, Object... args) {
        return getDbType().equals(databaseType);
    }

    /**
     * 返回该方言所属的数据库类型
     * @return 数据库类型
     */
    DatabaseType getDbType();
}
