package com.jason.test.component.conditiontype;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.ConditionTypeMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.InConditionTypeMapper;
import cool.lazy.cat.orm.core.jdbc.constant.ConditionConstant;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.express.ConditionExpressionSqlStringImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: mahao
 * @date: 2021-11-21 16:52
 */
@Component
public class InOrIsNullConditionTypeMapper extends InConditionTypeMapper implements ConditionTypeMapper {

    @PostConstruct
    public void registry() {
        ConditionConstant.CONDITION_TYPE_CACHE.put(InOrIsNull.class, new InOrIsNull());
    }

    @Override
    public boolean matched(ConditionType type) {
        return type instanceof InOrIsNull || super.matched(type);
    }

    @Override
    public ConditionExpressionSqlString map(ConditionType type, String paramName, Object val) {
        KeywordDictionary keywordDictionary = JdbcOperationSupport.getDialect().getKeywordDictionary();
        if (null == val) {
            return new ConditionExpressionSqlStringImpl(keywordDictionary.is() + " " + keywordDictionary.nul(), null);
        }
        return super.map(type, paramName, val);
    }
}
