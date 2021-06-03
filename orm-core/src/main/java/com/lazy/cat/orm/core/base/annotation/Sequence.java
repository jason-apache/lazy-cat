package com.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/4/23 19:44
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Sequence {

    /**
     * 指定库，支持占位符参数 ${schema}
     */
    String schema() default "";

    /**
     * 序列名称
     */
    String name();
}
