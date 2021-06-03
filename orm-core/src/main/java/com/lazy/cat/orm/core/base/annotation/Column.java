package com.lazy.cat.orm.core.base.annotation;


import com.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import com.lazy.cat.orm.core.jdbc.condition.ConditionType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/7 17:07
 * 定义pojo属性与数据库字段的映射关系
 * Column注解需要标注在pojo类getter方法，框架会检查与之对应的setter方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Column {

    /**
     * 字段名称，默认以字段名驼峰转换下划线
     */
    String name() default "";

    /**
     * 供api使用
     */
    ConditionType queryFilter() default ConditionType.NONE;

    /**
     * 指定一个类型转换器实现类
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     * @see com.lazy.cat.orm.core.jdbc.provider.impl.DefaultTypeConverterProvider#provider
     * 组件执行顺序为：
     *              onInsert：Validator --> TypeConverter --> Trigger
     *              onUpdate：Validator --> Trigger --> TypeConverter
     */
    Class<? extends TypeConverter> typeConverter() default TypeConverter.class;

    /**
     * 字段是否参与新增
     */
    boolean insertable() default true;

    /**
     * 字段是否参与修改
     */
    boolean updatable() default true;

    /**
     * 非空字段（新增、修改时校验）
     */
    boolean notNull() default false;

    /**
     * 仅对CharSequence实现类有效
     */
    int minLength() default -1;

    /**
     * 仅对CharSequence实现类有效
     */
    int maxLength() default -1;

    /**
     * 异常提示信息
     */
    String minLengthErrorMsg() default "";

    /**
     * 异常提示信息
     */
    String maxLengthErrorMsg() default "";

    /**
     * 校验器
     */
    Validator validator() default @Validator;
}
