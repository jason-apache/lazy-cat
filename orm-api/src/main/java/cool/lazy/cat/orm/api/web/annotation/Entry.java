package cool.lazy.cat.orm.api.web.annotation;

import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.core.base.annotation.Parameter;
import org.springframework.http.HttpMethod;

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
    Class<? extends ApiMethodEntry> api();

    /**
     * http请求方式
     */
    HttpMethod[] methods() default HttpMethod.POST;

    /**
     * @return 附加参数
     */
    Parameter[] parameters() default {};
}
