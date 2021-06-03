package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.jdbc.condition.ConditionType;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/11 12:39
 * 记录Column注解与pojo字段的映射信息
 */
public class TableFieldInfo {

    /**
     * pojo类型
     */
    protected Class<?> pojoType;
    /**
     * 字段的set方法
     */
    private Method setter;
    /**
     * 字段的get方法
     */
    private Method getter;
    /**
     * 数据库字段名称
     */
    private String dbFieldName;
    /**
     * 字段名称
     */
    private String javaFieldName;
    /**
     * 字段别名
     */
    private String aliasName;
    /**
     * 字段类型
     */
    private Class<?> javaType;
    /**
     * 字段列映射信息
     */
    private Column column;

    public void initColumn(com.lazy.cat.orm.core.base.annotation.Column column) {
        if (null == column) {
            return;
        }
        if (null == this.column) {
            this.column = new Column(column);
        }
    }

    public boolean havingQueryFilter() {
        return column.getQueryFilterType() != ConditionType.NONE;
    }

    public boolean havingValidator() {
        return column != null && column.havingValidator();
    }

    public boolean havingSimpleValidator() {
        return column != null && column.havingSimpleValidate();
    }

    public boolean havingTypeConverter() {
        return column != null && column.havingTypeConverter();
    }

    public boolean insertable() {
        return null != column && column.isInsertable();
    }

    public boolean updatable() {
        return null != column && column.isUpdatable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableFieldInfo that = (TableFieldInfo) o;

        if (!Objects.equals(pojoType, that.pojoType)) {
            return false;
        }
        if (!Objects.equals(setter, that.setter)) {
            return false;
        }
        return Objects.equals(getter, that.getter);
    }

    @Override
    public int hashCode() {
        int result = pojoType != null ? pojoType.hashCode() : 0;
        result = 31 * result + (setter != null ? setter.hashCode() : 0);
        result = 31 * result + (getter != null ? getter.hashCode() : 0);
        return result;
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public TableFieldInfo setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public Method getSetter() {
        return setter;
    }

    public TableFieldInfo setSetter(Method setter) {
        this.setter = setter;
        return this;
    }

    public Method getGetter() {
        return getter;
    }

    public TableFieldInfo setGetter(Method getter) {
        this.getter = getter;
        return this;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public TableFieldInfo setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
        return this;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    public TableFieldInfo setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
        return this;
    }

    public String getAliasName() {
        return aliasName;
    }

    public TableFieldInfo setAliasName(String aliasName) {
        this.aliasName = aliasName;
        return this;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public TableFieldInfo setJavaType(Class<?> javaType) {
        this.javaType = javaType;
        return this;
    }

    public Column getColumn() {
        return column;
    }

    public TableFieldInfo setColumn(Column column) {
        this.column = column;
        return this;
    }
}
