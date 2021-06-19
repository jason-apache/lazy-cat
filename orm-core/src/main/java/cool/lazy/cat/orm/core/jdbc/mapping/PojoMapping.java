package cool.lazy.cat.orm.core.jdbc.mapping;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 11:01
 */
public interface PojoMapping {

    /**
     * pojo类型
     */
    Class<?> getJavaType();

    /**
     * 联查条件
     */
    List<On> getJoinCondition();

    /**
     * 字段属性信息
     * @return
     */
    TableFieldInfo getFieldInfo();

    /**
     * 联查层级
     */
    int getCascadeLevel();

    /**
     * 映射对象是否参与新增
     */
    boolean isInsertable();

    /**
     * 映射对象是否参与修改
     */
    boolean isUpdatable();

    /**
     * 映射对象是否参与删除
     */
    boolean isDeletable();
}
