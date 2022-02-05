package cool.lazy.cat.orm.core.jdbc.adapter.mapper;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.IsNull;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.NotNull;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlStringImpl;

/**
 * @author: mahao
 * @date: 2021/7/19 16:45
 */
public class NullConditionMapper implements ConditionTypeMapper {

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof IsNull || type instanceof NotNull;
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        KeywordDictionary keywordDictionary = JdbcOperationSupport.getDialect().getKeywordDictionary();
        if (type instanceof IsNull) {
            return new ConditionExpressionSqlStringImpl(keywordDictionary.is() + " " + keywordDictionary.nul(), null);
        } else {
            return new ConditionExpressionSqlStringImpl(keywordDictionary.is() + " " + keywordDictionary.not() + " " + keywordDictionary.nul(), null);
        }
    }
}
