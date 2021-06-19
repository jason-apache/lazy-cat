package cool.lazy.cat.orm.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/11 17:28
 * 处理一对一映射关系，用来标注引用对象类型，不能和Column注解一同使用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OneToOne {

    /**
     * 关联条件，多个条件将以and处理
     */
    On[] condition();

    /**
     * 逻辑与OneToMany一致
     * @see OneToMany#cascadeLevel()
     */
    int cascadeLevel() default 1;

    /**
     * 映射对象是否参与新增
     * 逻辑与OneToMany一致
     * @see OneToMany#insertable()
     */
    boolean insertable() default true;

    /**
     * 映射对象是否参与修改
     * 逻辑与OneToMany一致
     * @see OneToMany#updatable()
     */
    boolean updatable() default true;

    /**
     * 映射对象是否参与删除
     * 逻辑与OneToMany一致
     * @see OneToMany#deletable()
     */
    boolean deletable() default false;
}
