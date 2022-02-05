package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;


import cool.lazy.cat.orm.core.jdbc.mapping.ColumnImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.Column;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/11 12:39
 * 记录Column注解与pojo字段的映射信息
 */
public class TableFieldInfo implements PojoField {

    /**
     * 字段的set方法
     */
    private final Method setter;
    /**
     * 字段的get方法
     */
    private final Method getter;
    /**
     * 字段类型
     */
    private final Class<?> javaType;
    /**
     * 数据库字段名称
     */
    private String dbFieldName;
    /**
     * 标注数据库列名是@Column注解明确指定的
     */
    private boolean specified;
    /**
     * 字段名称
     */
    private String javaFieldName;
    /**
     * 字段列映射信息
     */
    protected Column column;
    /**
     * 是否是一个关联其他pojo的键
     */
    private boolean foreignKey;

    public TableFieldInfo(Method getter, Method setter) {
        this.getter = getter;
        this.setter = setter;
        this.javaType = getter.getReturnType();
    }

    public void initColumn(cool.lazy.cat.orm.core.base.annotation.Column column) {
        if (null == column) {
            return;
        }
        if (null == this.column) {
            this.column = new ColumnImpl(column);
        }
    }

    @Override
    public boolean havingValidator() {
        return column != null && column.havingValidator();
    }

    @Override
    public boolean havingTypeConverter() {
        return column.havingTypeConverter();
    }

    @Override
    public boolean insertable() {
        return null != column && column.isInsertable();
    }

    @Override
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

        if (!Objects.equals(setter, that.setter)) {
            return false;
        }
        return Objects.equals(getter, that.getter);
    }

    @Override
    public int hashCode() {
        int result = setter != null ? setter.hashCode() : 0;
        result = 31 * result + (getter != null ? getter.hashCode() : 0);
        return result;
    }

    @Override
    public Method getSetter() {
        return setter;
    }

    @Override
    public Method getGetter() {
        return getter;
    }

    @Override
    public String getDbFieldName() {
        return dbFieldName;
    }

    @Override
    public boolean specified() {
        return specified;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public void setSpecified(boolean specified) {
        this.specified = specified;
    }

    @Override
    public String getJavaFieldName() {
        return javaFieldName;
    }

    public void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    @Override
    public Class<?> getJavaType() {
        return javaType;
    }

    @Override
    public Column getColumn() {
        return column;
    }

    @Override
    public boolean isForeignKey() {
        return foreignKey;
    }

    @Override
    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Override
    public String toString() {
        return javaFieldName;
    }
}
