package cool.lazy.cat.orm.core.jdbc.sql.dialect;

import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.CountSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.PageSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.sql.SequenceOperationSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.CountFunctionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2022-01-19 18:17
 */
public class SimplePostgreSqlDialect extends AbstractDialect implements PostgreSqlDialect {

    public SimplePostgreSqlDialect(FunctionHandlerAdapter functionHandlerAdapter) {
        super(functionHandlerAdapter);
    }

    @Override
    public List<SqlString> limitSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure) {
        List<SqlString> limitSql = new ArrayList<>(dqlSqlStructure.getSqlStrings());
        limitSql.add(new PageSqlString(this.getKeywordDictionary().limit()));
        limitSql.add(new PageSqlString(String.valueOf(searchParam.getPageSize())));
        limitSql.add(new PageSqlString("offset"));
        limitSql.add(new PageSqlString(String.valueOf(searchParam.getIndex())));
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
        List<SqlString> curValueSql = new ArrayList<>(6);
        curValueSql.add(new SequenceOperationSqlString(this.getKeywordDictionary().select()));
        curValueSql.add(new SequenceOperationSqlString("currval("));
        curValueSql.add(new SequenceOperationSqlString("'"));
        curValueSql.add(new DynamicNameSqlStringImpl(pojoType, schema, sequenceName, null, this.getDbFieldQuotationMarks()));
        curValueSql.add(new SequenceOperationSqlString("'"));
        curValueSql.add(new SequenceOperationSqlString(")"));
        return curValueSql;
    }

    @Override
    public List<SqlString> selectSequenceNextValueSql(Class<?> pojoType, String schema, String sequenceName) {
        List<SqlString> curValueSql = new ArrayList<>(6);
        curValueSql.add(new SequenceOperationSqlString(this.getKeywordDictionary().select()));
        curValueSql.add(new SequenceOperationSqlString("nextval("));
        curValueSql.add(new SequenceOperationSqlString("'"));
        curValueSql.add(new DynamicNameSqlStringImpl(pojoType, schema, sequenceName, null, this.getDbFieldQuotationMarks()));
        curValueSql.add(new SequenceOperationSqlString("'"));
        curValueSql.add(new SequenceOperationSqlString(")"));
        return curValueSql;
    }
}
