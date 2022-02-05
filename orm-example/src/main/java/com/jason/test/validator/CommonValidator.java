package com.jason.test.validator;

import com.jason.test.constant.ValidateConstant;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

/**
 * @author: mahao
 * @date: 2022-02-04 14:49
 */
public class CommonValidator implements Validator {

    public static final String VALIDATE_INFO_KEY = "VALIDATE_INFO_KEY";

    @Override
    public void validate(PojoField pojoField, Object value) {
        ValidateConstant.ValidateMessageInfo info = ValidateConstant.MessageInfoHolder.defaultInstance.get(pojoField.getColumn().getValidatorInfo().getParameterValue(VALIDATE_INFO_KEY));
        info.getAction().action(pojoField, value, info);
    }
}
