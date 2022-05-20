package cool.lazy.cat.orm.api.base.anno;

import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.base.method.ApiMethodEntryHook;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: mahao
 * @date: 2021/3/5 12:24
 * 指定pojo Api方法与框架内置方法的映射
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entry {

    /**
     * 访问路径
     */
    String path();

    /**
     * api方法, 必须是一个ApiMethodEntry的实现类
     */
    Class<? extends ApiMethodEntryHook> api();

    /**
     * http请求方式
     */
    HttpMethod[] methods() default HttpMethod.POST;

    /**
     * @return 附加参数
     */
    Parameter[] parameters() default {};
}
