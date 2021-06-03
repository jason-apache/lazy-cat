package com.jason.test.validator;


import com.lazy.cat.orm.core.jdbc.component.validator.Validator;
import com.lazy.cat.orm.core.jdbc.exception.ValidationFailedException;
import com.lazy.cat.orm.core.jdbc.mapping.Column;

/**
 * @author: mahao
 * @date: 2021/3/31 20:51
 */
public class SexValidator implements Validator {

    @Override
    public void validate(Column column, Object value) {
        if (!"女".equals(value)) {
            throw new ValidationFailedException(column.getValidatorInfo().getErrorMsg()[0]);
        }
    }
}
