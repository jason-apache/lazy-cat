package com.lazy.cat.orm.core.jdbc.dialect;


import com.lazy.cat.orm.core.jdbc.JdbcConstant;
import com.lazy.cat.orm.core.jdbc.dialect.type.DatabaseType;

/**
 * @author: mahao
 * @date: 2021/4/24 16:01
 */
public interface MysqlDialect extends Dialect {

    @Override
    default DatabaseType getDbType() {
        return JdbcConstant.DATABASE_TYPE_MYSQL;
    }
}
