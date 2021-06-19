package com.jason.test.validator;

import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.exception.ValidationFailedException;
import cool.lazy.cat.orm.core.jdbc.mapping.Column;

/**
 * @author: mahao
 * @date: 2021/3/31 20:53
 */
public class RegexValidator implements Validator {

    @Override
    public void validate(Column column, Object value) {
        String[] content = column.getValidatorInfo().getValidateContent();
        String[] errorMsg = column.getValidatorInfo().getErrorMsg();
        if (null == value) {
            throw new ValidationFailedException(errorMsg[0] + "#" + value);
        }
        if (!(value instanceof String)) {
            return;
        }
        for (int i = 0; i < content.length; i++) {
            if (!value.toString().matches(content[i])) {
                throw new ValidationFailedException(errorMsg[i] + "#" + value);
            }
        }
    }
}
