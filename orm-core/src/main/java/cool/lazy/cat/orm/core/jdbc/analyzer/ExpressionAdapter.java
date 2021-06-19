package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;

/**
 * @author: mahao
 * @date: 2021/4/9 20:08
 * 条件表达式适配器
 */
public interface ExpressionAdapter {

    /**
     * 向sql中注入条件表达式
     * @param filterType 条件类型
     * @param sql 原始sql
     * @param paramName 参数名称
     */
    void adapterConditionSymbol(ConditionType filterType, StringBuilder sql, String paramName);
}
