package cool.lazy.cat.orm.core.jdbc.param;

import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

/**
 * @author: mahao
 * @date: 2021/7/27 18:00
 * 删除操作基础参数
 */
public interface DeleteParam extends DataHolderParam, Param {

    /**
     * @param condition 删除条件
     */
    DeleteParam setCondition(SqlCondition condition);
}
