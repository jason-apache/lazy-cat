package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.NotIn;
import cool.lazy.cat.orm.core.jdbc.sql.string.ParameterNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.InConditionExpressionSqlString;

/**
 * @author: mahao
 * @date: 2021/7/19 16:38
 */
public class InConditionTypeMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof In || type instanceof NotIn;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        KeywordDictionary keywordDictionary = JdbcOperationSupport.getDialect().getKeywordDictionary();
        ParameterNameSqlStringImpl parameterName = new ParameterNameSqlStringImpl(paramName);
        if (type instanceof In) {
            return new InConditionExpressionSqlString(keywordDictionary.in(), parameterName);
        } else {
            return new InConditionExpressionSqlString(keywordDictionary.not() + " " + keywordDictionary.in(), parameterName);
        }
    }
}
