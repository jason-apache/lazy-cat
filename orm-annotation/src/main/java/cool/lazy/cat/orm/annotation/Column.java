package cool.lazy.cat.orm.annotation;


import cool.lazy.cat.orm.base.component.BaseTypeConverter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/7 17:07
 * 作为映射一个数据库表真实存在的列 定义pojo属性与数据库字段的映射关系
 * Column注解需要标注在pojo类getter方法，框架会检查与之对应的setter方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Column {

    /**
     * 字段名称，默认以字段名驼峰转换下划线
     */
    String name() default "";

    /**
     * 指定一个类型转换器实现类
     * 它可以是一个spring bean，应用程序将尝试从IOC容器中获取
     * 也可以是一个普通java对象，调用newInstance()完成初始化
     */
    Class<? extends BaseTypeConverter> typeConverter() default BaseTypeConverter.class;

    /**
     * 字段是否参与新增
     */
    boolean insertable() default true;

    /**
     * 字段是否参与修改
     */
    boolean updatable() default true;

    /**
     * 校验器
     */
    Validator validator() default @Validator;

    /**
     * 顺序
     */
    int sort() default 0;

    /**
     * @return 附加参数
     */
    Parameter[] parameters() default {};

    /**
     * 列描述, 可供生成DML使用
     */
    String description() default "";
}
