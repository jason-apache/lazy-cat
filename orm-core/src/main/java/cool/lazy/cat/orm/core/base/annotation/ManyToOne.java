package cool.lazy.cat.orm.core.base.annotation;

import cool.lazy.cat.orm.core.base.constant.JoinMode;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/12 11:24
 * 处理多对一映射关系，用来标注引用对象类型，不能和Column注解一同使用
 * eg：
 *      private Office office;
 *      @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), updatable = false, insertable = false)
 *      public Office getOffice() {
 *          return office;
 *      }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ManyToOne {

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
     * 逻辑与OneToMany一致
     * @see OneToMany#cascadeScope()
     */
    String[] cascadeScope() default {};

    /**
     * 逻辑与OneToMany一致
     * @see OneToMany#ignoreFields()
     */
    String[] ignoreFields() default {};

    /**
     * 映射对象是否参与新增
     * 逻辑与OneToMany一致
     * @see OneToMany#insertable()
     */
    boolean insertable() default false;

    /**
     * 映射对象是否参与修改
     * 逻辑与OneToMany一致
     * @see OneToMany#updatable()
     */
    boolean updatable() default false;

    /**
     * 映射对象是否参与删除
     * 逻辑与OneToMany一致
     * @see OneToMany#deletable()
     */
    boolean deletable() default false;

    /**
     * 顺序 决定了join关联顺序
     */
    int sort() default 0;

    /**
     * 关联模式
     */
    JoinMode mode() default JoinMode.LEFT_JOIN;
}
