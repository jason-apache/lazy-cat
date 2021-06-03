package com.lazy.cat.orm.core.jdbc.param;


import com.lazy.cat.orm.core.jdbc.condition.Condition;

/**
 * @author: mahao
 * @date: 2021/4/14 10:52
 */
public interface UpdateParam {

    /**
     * 需要更新的pojo类型
     * @return pojo类型
     */
    Class<?> getPojoType();

    /**
     * 更新条件
     * @return 更新条件
     */
    Condition getCondition();

    /**
     * 是否忽略空值的字段
     * @return 是否忽略空值
     */
    boolean getIgnoreNull();

    /**
     * 忽略更新的字段
     * @return 忽略更新的字段
     */
    String[] getIgnoreFields();

    /**
     * 需要更新的数据，可以是一个对象，也可以是一个对象集合
     * @return 数据
     */
    Object getData();
}
