package cool.lazy.cat.orm.annotation;


import cool.lazy.cat.orm.base.component.id.IdGenerator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/12 19:15
 * id注解必须和Column注解配合使用，pojo类中只能有一个id定义
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Id {

    /**
     * 指定一个id生成器类型
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     */
    Class<? extends IdGenerator> idGenerator();

    /**
     * @return 附加参数
     */
    Parameter[] parameters() default {};
}
