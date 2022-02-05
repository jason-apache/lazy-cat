package cool.lazy.cat.orm.core.jdbc.sql.condition;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/19 15:44
 * sql操作条件
 */
public interface SqlCondition {

    /**
     * @return 字段名
     */
    String field();

    /**
     * @return 值
     */
    Object value();

    /**
     * @return 条件类型
     */
    ConditionType type();

    /**
     * @return 条件连接的and条件
     */
    List<SqlCondition> getAnd();

    /**
     * @return 条件连接的or条件
     */
    List<SqlCondition> getOr();

    /**
     * @return 添加一个and关系的条件
     */
    SqlCondition and(SqlCondition condition);

    /**
     * @return 添加一个or关系的条件
     */
    SqlCondition or(SqlCondition condition);

    /**
     * @return 转换所有嵌套的条件为一个平铺的集合
     */
    List<SqlCondition> flatCondition();

    /**
     * @return 条件是否包含嵌套结构
     */
    default boolean nested() {
        if (null != getAnd()) {
            for (SqlCondition c : getAnd()) {
                if (null != c.getAnd() || null != c.getOr()) {
                    return true;
                }
            }
        }
        if (null != getOr()) {
            for (SqlCondition c : getOr()) {
                if (null != c.getAnd() || null != c.getOr()) {
                    return true;
                }
            }
        }
        return false;
    }
}
