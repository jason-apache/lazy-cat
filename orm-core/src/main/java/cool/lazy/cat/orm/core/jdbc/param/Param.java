package cool.lazy.cat.orm.core.jdbc.param;

import cool.lazy.cat.orm.core.jdbc.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/7/16 13:24
 * sql操作相关基本参数
 */
public interface Param {

    /**
     * 操作的pojo类型
     * @return pojo类型
     */
    Class<?> getPojoType();

    /**
     * 判断条件
     * @return 条件
     */
    SqlCondition getCondition();

    /**
     * 排除插件触发
     */
    Set<Class<? extends CommonComponent>> getExcludeComponents();
}
