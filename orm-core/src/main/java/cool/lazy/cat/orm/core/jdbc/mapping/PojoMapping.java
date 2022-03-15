package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.base.constant.JoinMode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 11:01
 */
public interface PojoMapping {

    /**
     * @return pojo类型
     */
    Class<?> getJavaType();

    /**
     * @return 联查条件
     */
    List<On> getJoinCondition();

    /**
     * @return 字段属性信息
     */
    PojoField getPojoField();

    /**
     * @return 联查层级
     */
    int getCascadeLevel();

    /**
     * @return 联查范围
     */
    String[] getCascadeScope();

    /**
     * @return 忽略查询字段
     */
    String[] getIgnoreFields();

    /**
     * @return 映射对象是否参与新增
     */
    boolean isInsertable();

    /**
     * @return 映射对象是否参与修改
     */
    boolean isUpdatable();

    /**
     * @return 映射对象是否参与删除
     */
    boolean isDeletable();

    /**
     * @return 排序
     */
    int sort();

    /**
     * @return 关联字段是否由映射对象赋值到源对象
     */
    boolean havingMappedToSource();

    void setHavingMappedToSource(boolean havingMappedToSource);

    /**
     * @return 关联查询条件
     */
    JoinMode getJoinMode();
}
