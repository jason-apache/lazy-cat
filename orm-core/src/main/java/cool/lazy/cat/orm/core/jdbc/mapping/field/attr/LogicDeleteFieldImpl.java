package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/4/14 17:46
 * 逻辑删除字段信息
 */
public class LogicDeleteFieldImpl extends TableFieldInfo implements LogicDeleteField {

    private final String deleteValue;
    private final String normalValue;

    public LogicDeleteFieldImpl(Method getter, Method setter, String deleteValue, String normalValue) {
        super(getter, setter);
        this.deleteValue = deleteValue;
        this.normalValue = normalValue;
    }

    @Override
    public String getDeleteValue() {
        return deleteValue;
    }

    @Override
    public String getNormalValue() {
        return normalValue;
    }
}
