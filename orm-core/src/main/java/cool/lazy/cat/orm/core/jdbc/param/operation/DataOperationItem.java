package cool.lazy.cat.orm.core.jdbc.param.operation;

import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/11/2 13:03
 * 操作数据参数
 */
public interface DataOperationItem extends Iterable<Object> {

    void setCondition(SqlCondition condition);

    /**
     * @return 操作条件
     */
    SqlCondition getCondition();

    Class<?> getPojoType();

    void add(Object pojo);

    /**
     * @return 数据集
     */
    Collection<Object> values();
}
