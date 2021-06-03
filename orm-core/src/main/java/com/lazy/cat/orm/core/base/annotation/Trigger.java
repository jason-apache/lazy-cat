package com.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/4/14 12:00
 * 新增、修改时的额外操作
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Trigger {

    /**
     * 指定一个触发器实现类
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     * 组件执行顺序为：
     *              onInsert：Validator --> TypeConverter --> Trigger
     *              onUpdate：Validator --> Trigger --> TypeConverter
     * @see com.lazy.cat.orm.core.jdbc.provider.impl.DefaultTriggerProvider#provider
     */
    Class<? extends com.lazy.cat.orm.core.jdbc.component.trigger.Trigger> type();

    /**
     * 触发器在新增时触发
     */
    boolean onInsert() default true;

    /**
     * 触发器在修改时触发
     */
    boolean onUpdate() default true;

    /**
     * 触发器的执行顺序
     */
    int sort() default 0;
}
