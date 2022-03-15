package cool.lazy.cat.orm.annotation;

import cool.lazy.cat.orm.base.component.BaseValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/31 16:39
 * 对pojo类属性字段的增强控制
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    /**
     * 指定一个参数校验器实现类
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     */
    Class<? extends BaseValidator> type() default BaseValidator.class;

    /**
     * 非空字段
     */
    boolean notNull() default false;

    /**
     * @return 附加参数
     */
    Parameter[] parameter() default {};
}
