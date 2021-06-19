package cool.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/3/12 12:40
 * 联查条件
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface On {

    /**
     * 对象属性名（java字段名）
     */
    String foreignFiled();

    /**
     * 映射的对象的属性名（java字段名）
     */
    String targetFiled();
}
