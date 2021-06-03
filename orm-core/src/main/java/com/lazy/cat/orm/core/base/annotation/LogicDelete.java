package com.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/4/14 17:27
 * 逻辑删除字段，需要和Column注解搭配使用，pojo类中只能有一个逻辑删除字段定义
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface LogicDelete {

    /**
     *  标记数据已被逻辑删除的值
     */
    String deleteValue();

    /**
     * 标记数据未被逻辑删除的值
     */
    String normalValue();
}
