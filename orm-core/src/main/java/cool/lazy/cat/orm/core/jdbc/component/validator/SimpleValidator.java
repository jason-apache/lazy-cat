package cool.lazy.cat.orm.core.jdbc.component.validator;


import cool.lazy.cat.orm.core.jdbc.exception.ValidationFailedException;
import cool.lazy.cat.orm.core.jdbc.mapping.Column;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/31 20:17
 */
public class SimpleValidator implements Validator {

    @Override
    public void validate(Column column, Object value) {
        if (value instanceof Collection) {
            this.validateForCollectionObj(column, (Collection<?>) value);
        } else {
            this.validateForSingleObj(column, value);
        }
    }

    private void validateForCollectionObj(Column column, Collection<?> values) {
        values.forEach(v -> this.validateForSingleObj(column, v));
    }

    private void validateForSingleObj(Column column, Object value) {
        if (column.getNotNull() && value == null) {
            throw new ValidationFailedException("参数不能为空！#" + column.getName());
        }
        if (!(value instanceof CharSequence)) {
            return;
        }
        if (column.getMinLength() > 0 && ((CharSequence) value).length() < column.getMinLength()) {
            throw new ValidationFailedException(column.getMinLengthErrorMsg());
        }
        if (column.getMaxLength() > -1 && ((CharSequence) value).length() > column.getMaxLength()) {
            throw new ValidationFailedException(column.getMaxLengthErrorMsg());
        }
    }
}
