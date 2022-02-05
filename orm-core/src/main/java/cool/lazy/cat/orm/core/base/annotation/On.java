package cool.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/12 12:40
 * 联查条件
 */
@Target({})
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

    /**
     * 关联字段赋值方式 insert|update时取值方式
     */
    AssignmentMethod assignmentMethod() default AssignmentMethod.INFER_SOURCE_TO_MAPPED;

    enum AssignmentMethod {

        /**
         * 源对象关联字段赋值至被映射的对象的对应的字段
         */
        SOURCE_TO_MAPPED,
        /**
         * 被映射的对象的对应的字段赋值至源对象关联字段
         */
        MAPPED_TO_SOURCE,
        /**
         * 自动推导 映射一方的字段若为主键 则赋值给另一方 否则默认取SOURCE_TO_MAPPED逻辑
         */
        INFER_SOURCE_TO_MAPPED,
        /**
         * 自动推导 映射一方的字段若为主键 则赋值给另一方 否则默认取MAPPED_TO_SOURCE逻辑
         */
        INFER_MAPPED_TO_SOURCE
    }
}
