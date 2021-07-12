package cool.lazy.cat.orm.api.web.entrust.executor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/7/8 13:15
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {

    String name();

    String defaultValue() default "";

    boolean required() default false;
}
