package cool.lazy.cat.orm.core.jdbc.adapter.mapper;


import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;

/**
 * @author: mahao
 * @date: 2021/4/9 20:08
 * 条件表达式映射
 */
public interface ConditionTypeMapper {

    boolean matched(ConditionType type);

    /**
     * 向sql中注入条件表达式
     * @param type 条件类型
     * @param paramName 参数名称
     * @param val 参数值
     */
    ConditionExpressionSqlString map(ConditionType type, String paramName, Object val);
}
