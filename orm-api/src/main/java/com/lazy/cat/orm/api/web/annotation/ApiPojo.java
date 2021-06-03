package com.lazy.cat.orm.api.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/5 10:14
 * 标注一个pojo需要参与Api自动映射
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ApiPojo {

    /**
     * api的唯一标识，也就是api访问路径的根
     */
    String nameSpace() default "";

    /**
     * Api方法
     */
    Entry[] entry();
}
