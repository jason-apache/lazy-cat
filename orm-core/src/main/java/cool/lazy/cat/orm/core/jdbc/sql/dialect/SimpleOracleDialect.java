package cool.lazy.cat.orm.core.jdbc.sql.dialect;


import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.CountSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql.PageSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.sql.SequenceOperationSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.NormalConditionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.CountFunctionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlStringImpl;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 20:25
 */
public class SimpleOracleDialect extends AbstractDialect implements OracleDialect {

    private static final String ROWNUM = "ROWNUM";
    private static final String ROWNUM_ALIAS_NAME = " rownum_";
    private static final String TEMP = "temp_";

    public SimpleOracleDialect(FunctionHandlerAdapter functionHandlerAdapter) {
        super(functionHandlerAdapter);
    }

    @Override
    public List<SqlString> limitSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure) {
        KeywordDictionary keywordDictionary = this.getKeywordDictionary();
        List<SqlString> limitSql = new ArrayList<>(dqlSqlStructure.getSqlStrings().size() + 20);
        limitSql.add(new PageSqlString(keywordDictionary.select()));
        limitSql.add(new PageSqlString("*"));
        limitSql.add(new PageSqlString(keywordDictionary.from()));
        if (searchParam.getIndex() > 0) {
            limitSql.add(new PageSqlString("("));
            limitSql.add(new PageSqlString(keywordDictionary.select()));
            limitSql.add(new PageSqlString(TEMP));
            limitSql.add(new PageSqlString(".*,"));
            limitSql.add(new PageSqlString(ROWNUM));
            limitSql.add(new PageSqlString(ROWNUM_ALIAS_NAME));
            limitSql.add(new PageSqlString(keywordDictionary.from()));
            limitSql.add(new PageSqlString("("));
            limitSql.addAll(dqlSqlStructure.getSqlStrings());
            limitSql.add(new PageSqlString(")"));
            limitSql.add(new PageSqlString(TEMP));
            WhereSqlString<NormalConditionSqlString> internal = new WhereSqlStringImpl<>();
            internal.combination(new NormalConditionSqlString(ROWNUM + "<=" + (searchParam.getIndex() + searchParam.getPageSize())));
            limitSql.add(internal);
            limitSql.add(new PageSqlString(")"));
            WhereSqlString<NormalConditionSqlString> outside = new WhereSqlStringImpl<>();
            outside.combination(new NormalConditionSqlString(ROWNUM_ALIAS_NAME + ">" + searchParam.getIndex()));
            limitSql.add(outside);
        } else {
            limitSql.add(new PageSqlString("("));
            limitSql.addAll(dqlSqlStructure.getSqlStrings());
            limitSql.add(new PageSqlString(")"));
            limitSql.add(new PageSqlString(TEMP));
            WhereSqlString<NormalConditionSqlString> outside = new WhereSqlStringImpl<>();
            outside.combination(new NormalConditionSqlString(ROWNUM + "<=" + searchParam.getPageSize()));
            limitSql.add(outside);
        }
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
        KeywordDictionary keywordDictionary = this.getKeywordDictionary();
        List<SqlString> curValueSql = new ArrayList<>(5);
        curValueSql.add(new SequenceOperationSqlString(keywordDictionary.select()));
        DynamicNameSqlString dynamicNameSqlString = new DynamicNameSqlStringImpl(pojoType, schema, sequenceName, null, this.getDbFieldQuotationMarks());
        curValueSql.add(dynamicNameSqlString);
        curValueSql.add(new SequenceOperationSqlString(".currval"));
        curValueSql.add(new SequenceOperationSqlString(keywordDictionary.from()));
        curValueSql.add(new SequenceOperationSqlString("dual"));
        return curValueSql;
    }

    @Override
    public List<SqlString> selectSequenceNextValueSql(Class<?> pojoType, String schema, String sequenceName) {
        KeywordDictionary keywordDictionary = this.getKeywordDictionary();
        List<SqlString> nextValueSql = new ArrayList<>(5);
        nextValueSql.add(new SequenceOperationSqlString(keywordDictionary.select()));
        DynamicNameSqlString dynamicNameSqlString = new DynamicNameSqlStringImpl(pojoType, schema, sequenceName, null, this.getDbFieldQuotationMarks());
        nextValueSql.add(dynamicNameSqlString);
        nextValueSql.add(new SequenceOperationSqlString(".nextval"));
        nextValueSql.add(new SequenceOperationSqlString(keywordDictionary.from()));
        nextValueSql.add(new SequenceOperationSqlString("dual"));
        return nextValueSql;
    }
}
