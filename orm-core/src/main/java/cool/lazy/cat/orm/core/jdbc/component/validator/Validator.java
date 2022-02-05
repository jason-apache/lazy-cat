package cool.lazy.cat.orm.core.jdbc.component.validator;

import cool.lazy.cat.orm.core.jdbc.component.SpecialColumn;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;

/**
 * @author: mahao
 * @date: 2021/3/31 13:25
 */
public interface Validator extends SpecialColumn {

    /**
     * 对属性值进行校验，不满足条件时抛出ValidationFailedException
     * @param pojoField 字段列信息
     * @param value 字段值
     */
    void validate(PojoField pojoField, Object value);

    @Override
    default void process(SqlSource sqlSource, PojoField pojoField) {
        this.validate(pojoField, sqlSource.getValue(pojoField.getJavaFieldName()));
    }
}
