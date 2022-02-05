package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.KeywordSymbol;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlStringImpl;

/**
 * @author: mahao
 * @date: 2021/7/19 16:00
 */
public class KeywordConditionMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof KeywordSymbol;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        return new ConditionExpressionSqlStringImpl(type.getSymbol(), new ParameterNameSqlStringImpl(paramName));
    }
}
