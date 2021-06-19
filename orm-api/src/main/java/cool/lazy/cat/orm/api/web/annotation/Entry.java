package cool.lazy.cat.orm.api.web.annotation;

import org.springframework.web.bind.annotation.RequestMethod;

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
     * 需要转发的目标路径
     */
    String mappingApi();

    /**
     * http请求方式
     */
    RequestMethod method() default RequestMethod.POST;
}
