package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

/**
 * @author: mahao
 * @date: 2021/10/18 11:21
 * 对象关联条件
 */
public interface On {

    /**
     * @return 对象类型
     */
    Class<?> getForeignPojoType();

    /**
     * @return 关联对象类型
     */
    Class<?> getTargetPojoType();

    /**
     * @return 对象字段名称
     */
    String getForeignField();

    /**
     * @return 关联对象字段名称
     */
    String getTargetFiled();

    /**
     * @return 赋值方式
     */
    cool.lazy.cat.orm.annotation.On.AssignmentMethod getAssignmentMethod();

    /**
     * @return 对象字段信息
     */
    PojoField getForeignKeyInfo();

    void setForeignKeyInfo(PojoField foreignKeyInfo);

    /**
     * @return 关联对象字段信息
     */
    PojoField getTargetFiledInfo();

    void setTargetFiledInfo(PojoField targetFiledInfo);

    /**
     * @return 关联字段是否由映射对象赋值到源对象
     */
    boolean isMappedToSource();

    void setMappedToSource(boolean mappedToSource);
}
