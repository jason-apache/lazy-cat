package cool.lazy.cat.orm.core.jdbc.sql.string.condition.express;

import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/10/9 13:39
 */
public interface ConditionExpressionSqlString extends NormalSqlString, SqlString {

    /**
     * @return 条件表达式 [=, !=, >, >=, <, <=, like, in, ......]
     */
    String getExpression();

    /**
     * @return 参数名称 与sqlSource中的属性名称对应
     */
    ParameterNameSqlString getParameterName();

    /**
     * 条件表达式实例无需关注大小写
     * @param charCase 字符大小写
     */
    @Override
    default void changeCase(Case charCase) {}
}
