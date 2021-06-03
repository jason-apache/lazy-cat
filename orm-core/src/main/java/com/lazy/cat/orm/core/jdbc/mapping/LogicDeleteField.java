package com.lazy.cat.orm.core.jdbc.mapping;

/**
 * @author: mahao
 * @date: 2021/4/14 17:46
 * 逻辑删除字段信息
 */
public class LogicDeleteField extends TableFieldInfo {

    private String deleteValue;
    private String normalValue;

    public String getDeleteValue() {
        return deleteValue;
    }

    public LogicDeleteField setDeleteValue(String deleteValue) {
        this.deleteValue = deleteValue;
        return this;
    }

    public String getNormalValue() {
        return normalValue;
    }

    public LogicDeleteField setNormalValue(String normalValue) {
        this.normalValue = normalValue;
        return this;
    }
}
