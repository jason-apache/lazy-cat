package com.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/3/8 12:42
 * 映射对象与数据库表映射关系
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * 表名，默认以类名驼峰转换下划线
     */
    String tableName() default "";

    /**
     * 指定库，支持占位符参数 ${schema}
     * @see com.lazy.cat.orm.core.jdbc.JdbcConfig#getDbSchema()
     */
    String schema() default "";
}
