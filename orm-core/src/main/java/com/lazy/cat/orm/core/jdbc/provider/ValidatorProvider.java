package com.lazy.cat.orm.core.jdbc.provider;


import com.lazy.cat.orm.core.jdbc.component.validator.Validator;

/**
 * @author: mahao
 * @date: 2021/3/31 13:44
 * 参数校验器提供者
 */
public interface ValidatorProvider {

    /**
     * 根据类型提供一个参数校验器
     * @param validatorType 参数校验器类型
     * @return 参数校验器
     */
    Validator provider(Class<? extends Validator> validatorType);
}
