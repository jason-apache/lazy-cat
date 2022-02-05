package cool.lazy.cat.orm.core.jdbc.sql.dialect;


import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.CountSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.PageSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.CountFunctionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 19:22
 */
public class SimpleMysqlDialect extends AbstractDialect implements MysqlDialect {

    public SimpleMysqlDialect(FunctionHandlerAdapter functionHandlerAdapter) {
        super(functionHandlerAdapter);
    }

    @Override
    public List<SqlString> limitSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure) {
        List<SqlString> limitSql = new ArrayList<>(dqlSqlStructure.getSqlStrings());
        limitSql.add(new PageSqlString(this.getKeywordDictionary().limit()));
        limitSql.add(new PageSqlString(String.valueOf(searchParam.getIndex()), true));
        limitSql.add(new PageSqlString(",", true));
        limitSql.add(new PageSqlString(String.valueOf(searchParam.getPageSize()), true));
        return limitSql;
    }

    @Override
    public List<SqlString> countSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure) {
        KeywordDictionary keywordDictionary = this.getKeywordDictionary();
        List<SqlString> countSql = new ArrayList<>(13);
        countSql.add(new CountSqlString(keywordDictionary.select()));
        CountFunctionSqlString countFunctionKeywordSqlString = new CountFunctionSqlString("0");
        this.handleFunctionSqlString(countFunctionKeywordSqlString);
        countSql.add(countFunctionKeywordSqlString);
        countSql.add(new CountSqlString(keywordDictionary.from()));
        countSql.add(new CountSqlString("("));
        countSql.add(new CountSqlString(keywordDictionary.select()));
        String idKeyword = JdbcConstant.MAIN_TABLE_NAME + "." + searchParam.getTableInfo().getId().getDbFieldName();
        countSql.add(new CountSqlString(idKeyword));
        // 复制源结构的from子句
        countSql.add(dqlSqlStructure.getFrom());
        // 复制源结构的where子句
        if (null != dqlSqlStructure.getWhere()) {
            countSql.add(dqlSqlStructure.getWhere());
        }
        if (searchParam.getTableInfo().havingOneToManyMapping()) {
            countSql.add(new CountSqlString(keywordDictionary.group()));
            countSql.add(new CountSqlString(keywordDictionary.by()));
            countSql.add(new CountSqlString(idKeyword));
        }
        countSql.add(new CountSqlString(")"));
        countSql.add(new CountSqlString(JdbcConstant.COUNT_TABLE_NAME));
        return countSql;
    }

    @Override
    public List<SqlString> selectSequenceCurrentValueSql(Class<?> pojoType, String schema, String sequenceName) {
        throw new UnsupportedOperationException("MySQL不支持原生序列");
    }

    @Override
    public List<SqlString> selectSequenceNextValueSql(Class<?> pojoType, String schema, String sequenceName) {
        throw new UnsupportedOperationException("MySQL不支持原生序列");
    }
}
