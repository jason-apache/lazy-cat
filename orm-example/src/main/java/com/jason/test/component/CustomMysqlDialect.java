package com.jason.test.component;

import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.SimpleMysqlDialect;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/20 15:07
 * 自定义mysql序列id实现
 */
@Component
public class CustomMysqlDialect extends SimpleMysqlDialect {

    public CustomMysqlDialect(FunctionHandlerAdapter functionHandlerAdapter) {
        super(functionHandlerAdapter);
    }

    @Override
    public List<SqlString> selectSequenceCurrentValueSql(Class<?> pojoType, String schema, String sequenceName) {
        return Collections.singletonList(new NormalSqlStringImpl(this.getKeywordDictionary().select() + " seq_curval('" + sequenceName + "')"));
    }

    @Override
    public List<SqlString> selectSequenceNextValueSql(Class<?> pojoType, String schema, String sequenceName) {
        return Collections.singletonList(new NormalSqlStringImpl(this.getKeywordDictionary().select() + " seq_nextval('" + sequenceName + "')"));
    }

    @Override
    public Case getDefaultCharacterCase() {
        return super.getDefaultCharacterCase();
    }
}
