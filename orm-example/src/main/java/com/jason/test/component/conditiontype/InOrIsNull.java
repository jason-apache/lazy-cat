package com.jason.test.component.conditiontype;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;

/**
 * @author: mahao
 * @date: 2021-11-21 16:51
 */
public class InOrIsNull extends In implements ConditionType {

    @Override
    public String getSymbol() {
        return null;
    }
}
