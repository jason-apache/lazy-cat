package cool.lazy.cat.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2022-01-28 21:26
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {

    /**
     * @return 参数名
     */
    String name();

    /**
     * @return 参数值
     */
    String value();
}
