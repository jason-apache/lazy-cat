package cool.lazy.cat.orm.core.base.annotation;

import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultTriggerProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/4/14 12:00
 * 新增、修改时的额外操作
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Trigger {

    /**
     * 指定一个触发器实现类
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     * @see DefaultTriggerProvider#provider
     */
    Class<? extends cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger> type();

    /**
     * 触发器的执行顺序
     */
    int sort() default 0;
}
