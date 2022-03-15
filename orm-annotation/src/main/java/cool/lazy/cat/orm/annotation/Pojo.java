package cool.lazy.cat.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/28 17:05
 * 注册一个pojo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Pojo {

    /**
     * 触发器
     */
    Trigger[] trigger() default {};

    /**
     * 数据库表
     */
    Table table() default @Table;
}
