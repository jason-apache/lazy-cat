package com.lazy.cat.orm.core.jdbc.mapping;

/**
 * @author: mahao
 * @date: 2021/3/12 13:19
 * 对象关联条件
 */
public class On {

    private Class<?> foreignPojoType;
    private Class<?> targetPojoType;
    private String foreignField;
    private String targetFiled;
    private TableFieldInfo foreignKeyInfo;
    private TableFieldInfo targetFiledInfo;

    public On(com.lazy.cat.orm.core.base.annotation.On on) {
        this.foreignField = on.foreignFiled();
        this.targetFiled = on.targetFiled();
    }

    public Class<?> getForeignPojoType() {
        return foreignPojoType;
    }

    public On setForeignPojoType(Class<?> foreignPojoType) {
        this.foreignPojoType = foreignPojoType;
        return this;
    }

    public Class<?> getTargetPojoType() {
        return targetPojoType;
    }

    public On setTargetPojoType(Class<?> targetPojoType) {
        this.targetPojoType = targetPojoType;
        return this;
    }

    public String getForeignField() {
        return foreignField;
    }

    public On setForeignField(String foreignField) {
        this.foreignField = foreignField;
        return this;
    }

    public String getTargetFiled() {
        return targetFiled;
    }

    public On setTargetFiled(String targetFiled) {
        this.targetFiled = targetFiled;
        return this;
    }

    public TableFieldInfo getForeignKeyInfo() {
        return foreignKeyInfo;
    }

    public On setForeignKeyInfo(TableFieldInfo foreignKeyInfo) {
        this.foreignKeyInfo = foreignKeyInfo;
        return this;
    }

    public TableFieldInfo getTargetFiledInfo() {
        return targetFiledInfo;
    }

    public On setTargetFiledInfo(TableFieldInfo targetFiledInfo) {
        this.targetFiledInfo = targetFiledInfo;
        return this;
    }
}
