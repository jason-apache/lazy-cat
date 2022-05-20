package cool.lazy.cat.orm.core.jdbc.adapter;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/19 16:22
 * 条件表达式适配器
 */
public interface ConditionTypeAdapter {

    /**
     * 根据条件适配对应的表达式
     * @param type 条件
     * @param paramName 参数名
     * @param val 参数值
     * @return 条件表达式
     */
    ConditionExpressionSqlString adapt(ConditionType type, String paramName, Object val);
}
