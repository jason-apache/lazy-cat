package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/4/14 10:52
 * 更新操作基础参数
 */
public interface UpdateParam extends DataHolderParam, Param {

    /**
     * 是否忽略空值的字段
     * @return 是否忽略空值
     */
    boolean ignoreNullField();

    /**
     * @return 忽略更新字段
     */
    Set<String> getUpdateFields();

    /**
     * @param condition 更新条件
     */
    void setCondition(SqlCondition condition);

    /**
     * @param pojoType 更新pojo类型
     */
    void setPojoType(Class<?> pojoType);
}
