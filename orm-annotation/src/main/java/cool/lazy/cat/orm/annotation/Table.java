package cool.lazy.cat.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/8 12:42
 * 映射对象与数据库表映射关系
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * 表名，默认以类名驼峰转换下划线
     */
    String tableName() default "";

    /**
     * 指定库
     */
    String schema() default "";

    /**
     * 表描述, 可供生成DML使用
     */
    String description() default "";

    /**
     * @return 附加参数
     */
    Parameter[] parameter() default {};
}
