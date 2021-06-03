package com.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/3/31 16:39
 * 对pojo类属性字段的增强控制
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    /**
     * 指定一个参数校验器实现类
     * 根据字段Column注解的insertable、updatable属性触发校验器
     * @see Column#insertable()
     * @see Column#updatable()
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     * 组件执行顺序为：
     *              onInsert：Validator --> TypeConverter --> Trigger
     *              onUpdate：Validator --> Trigger --> TypeConverter
     * @see com.lazy.cat.orm.core.jdbc.provider.impl.DefaultValidatorProvider#provider
     */
    Class<? extends com.lazy.cat.orm.core.jdbc.component.validator.Validator> type() default com.lazy.cat.orm.core.jdbc.component.validator.Validator.class;

    /**
     * 非空字段
     */
    boolean notNull() default false;

    /**
     * 验证内容，参数可以为空
     */
    String[] validateContent() default {};

    /**
     * 异常信息
     */
    String[] errorMsg() default {};
}
