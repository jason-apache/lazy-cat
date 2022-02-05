package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;

import cool.lazy.cat.orm.core.jdbc.mapping.Column;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/7/29 17:48
 * 对象字段
 */
public interface PojoField {

    /**
     * @return java字段名称
     */
    String getJavaFieldName();

    /**
     * @return 数据库列名
     */
    String getDbFieldName();

    /**
     * @return 数据库列名是@Column注解明确指定的
     */
    boolean specified();

    /**
     * @return get方法
     */
    Method getGetter();

    /**
     * @return 是否存在校验器
     */
    boolean havingValidator();

    /**
     * @return 是否存在类型转换器
     */
    boolean havingTypeConverter();

    /**
     * @return 是否参与新增
     */
    boolean insertable();

    /**
     * @return 是否参与更新
     */
    boolean updatable();

    /**
     * @return set方法
     */
    Method getSetter();

    /**
     * @return 字段java类型
     */
    Class<?> getJavaType();

    /**
     * @return 列信息
     */
    Column getColumn();

    /**
     * @return 字段是否为外键(关联|被关联字段)
     */
    boolean isForeignKey();

    void setForeignKey(boolean foreignKey);
}
