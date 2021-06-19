package cool.lazy.cat.orm.core.jdbc.component.validator;

import cool.lazy.cat.orm.core.jdbc.mapping.Column;

/**
 * @author: mahao
 * @date: 2021/3/31 13:25
 */
public interface Validator {

    /**
     * 对属性值进行校验，不满足条件时抛出ValidationFailedException
     * @param column 字段列信息
     * @param value 属性值
     */
    void validate(Column column, Object value);
}
