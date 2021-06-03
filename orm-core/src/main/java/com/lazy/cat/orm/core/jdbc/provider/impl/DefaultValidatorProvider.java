package com.lazy.cat.orm.core.jdbc.provider.impl;

import com.lazy.cat.orm.core.base.exception.InitFailedException;
import com.lazy.cat.orm.core.jdbc.component.validator.Validator;
import com.lazy.cat.orm.core.jdbc.provider.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/31 13:45
 */
public class DefaultValidatorProvider implements ValidatorProvider {

    protected Map<Class<? extends Validator>, Validator> validatorMap = new HashMap<>();

    @Autowired(required = false)
    private void initValidatorMap(List<Validator> validatorList) {
        this.validatorMap = validatorList.stream().collect(Collectors.toMap(Validator::getClass, Function.identity()));
    }

    @Override
    public Validator provider(Class<? extends Validator> validatorType) {
        Validator validator = this.validatorMap.get(validatorType);
        if (null == validator) {
            try {
                validator = validatorType.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new InitFailedException("初始化validator失败！");
            }
            this.validatorMap.put(validatorType, validator);
        }
        return validator;
    }
}
