package com.lazy.cat.orm.core.jdbc.analyzer;

import com.lazy.cat.orm.core.jdbc.KeyWordConverter;
import com.lazy.cat.orm.core.jdbc.condition.ConditionType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: mahao
 * @date: 2021/4/9 20:10
 */
public class DefaultExpressionAdapter implements ExpressionAdapter {
    
    @Autowired
    protected KeyWordConverter keyWordConverter;
    
    @Override
    public void adapterConditionSymbol(ConditionType filterType, StringBuilder sql, String paramName) {
        if (filterType == ConditionType.NONE) {
            return;
        }
        if (filterType == ConditionType.ALL_LIKE) {
            sql.append(keyWordConverter.like()).append(filterType.getSymbol()).append(" ").append(":").append(paramName)
                    .append(" ").append(filterType.getSymbol()).append(" ");
        } else if (filterType == ConditionType.RIGHT_LIKE) {
            sql.append(keyWordConverter.like()).append(":").append(paramName).append(" ").append(filterType.getSymbol()).append(" ");
        } else if (filterType == ConditionType.LEFT_LIKE) {
            sql.append(keyWordConverter.like()).append(filterType.getSymbol()).append(" ").append(":").append(paramName).append(" ");
        } else if (filterType == ConditionType.IN) {
            sql.append(keyWordConverter.in()).append("(").append(" ").append(":").append(paramName).append(" ").append(")");
        } else if (filterType == ConditionType.NOT_IN) {
            sql.append(keyWordConverter.not()).append(keyWordConverter.in()).append("(").append(" ").append(":").append(paramName).append(" ").append(")");
        } else if (filterType == ConditionType.IS_NULL) {
            sql.append(keyWordConverter.is()).append(keyWordConverter.nul());
        } else if (filterType == ConditionType.NOT_NULL) {
            sql.append(keyWordConverter.is()).append(keyWordConverter.not()).append(keyWordConverter.nul());
        } else {
            sql.append(filterType.getSymbol()).append(" ").append(":").append(paramName).append(" ");
        }
    }
}
