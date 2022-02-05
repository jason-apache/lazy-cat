package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.None;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/19 16:47
 */
public class NoneConditionMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof None;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        return null;
    }
}
