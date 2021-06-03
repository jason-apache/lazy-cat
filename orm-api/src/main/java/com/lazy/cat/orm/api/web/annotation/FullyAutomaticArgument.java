package com.lazy.cat.orm.api.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/7 14:02
 * 标注方法参数需要经过Api推导类型，交由定义的参数解析器解析处理
 * 只有涉及了pojo参数传递的APi参数才需要特殊处理，在明确参数类型时，请避免使用此注解
 * @see com.lazy.cat.orm.api.config.FullyAutomaticArgumentResolver
 * @see com.lazy.cat.orm.api.web.entrust.controller.EntrustController#save
 * @see com.lazy.cat.orm.api.web.entrust.controller.EntrustController#saveCascade
 * @see com.lazy.cat.orm.api.web.entrust.controller.EntrustController#remove
 * @see com.lazy.cat.orm.api.web.entrust.controller.EntrustController#removeCascade
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FullyAutomaticArgument {
}
