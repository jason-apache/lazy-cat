package com.lazy.cat.orm.core.jdbc.dialect.type;

/**
 * @author: mahao
 * @date: 2021/4/24 16:07
 * 数据库类型
 */
public interface DatabaseType {

    /**
     * 返回数据库类型名称
     * @return 数据库名称
     */
    String getName();
}
