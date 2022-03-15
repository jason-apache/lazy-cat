package cool.lazy.cat.orm.api.web.annotation;

import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2022-02-05 15:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApiQueryFilter {

    /**
     * @return 字段查询过滤条件
     */
    Class<? extends ConditionType> value();

    /**
     *
     * @return 附加参数
     */
    Parameter[] parameters() default {};
}
