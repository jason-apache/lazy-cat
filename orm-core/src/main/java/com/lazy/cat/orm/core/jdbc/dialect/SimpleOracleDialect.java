package com.lazy.cat.orm.core.jdbc.dialect;


import com.lazy.cat.orm.core.base.util.StringUtil;
import com.lazy.cat.orm.core.jdbc.JdbcConstant;
import com.lazy.cat.orm.core.jdbc.KeyWordConverter;
import com.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 20:25
 */
public class SimpleOracleDialect implements OracleDialect {

    @Autowired
    protected KeyWordConverter keyWordConverter;
    private static final String ROWNUM = "ROWNUM";
    private static final String ROWNUM_ALIAS_NAME = " rownum_ ";
    private static final String TEMP = "temp_";

    @Override
    public String limitSql(SearchParam searchParam, StringBuilder originalSql, List<TableFieldInfoIndexWrapper> indexes) {
        StringBuilder sql = new StringBuilder();
        if (searchParam.getIndex() > 0) {
            sql.append(keyWordConverter.select()).append("* ").append(keyWordConverter.from()).append("( ").append(keyWordConverter.select()).append(TEMP)
               .append(".*, ").append(ROWNUM).append(ROWNUM_ALIAS_NAME).append(keyWordConverter.from()).append("(").append(originalSql).append(") ")
               .append(TEMP).append(" ").append(keyWordConverter.where()).append(ROWNUM).append(" <= ").append(searchParam.getIndex() + searchParam.getPageSize())
               .append(") ").append(keyWordConverter.where()).append(ROWNUM_ALIAS_NAME).append("> ").append(searchParam.getIndex());
            // 查询了rownum协助分页，并将此列的空映射追加至末尾，避免NumberOfFieldsMismatchException
            indexes.add(null);
        } else {
            sql.append(keyWordConverter.select()).append("* ").append(keyWordConverter.from()).append("(").append(originalSql).append(") ")
               .append(TEMP).append(" ").append(keyWordConverter.where()).append(ROWNUM).append(" <= ").append(searchParam.getPageSize());
        }
        return sql.toString();
    }

    @Override
    public String countSql(SearchParam searchParam, StringBuilder originalSql) {
        StringBuilder sql = new StringBuilder();
        if (searchParam.getTableInfo().havingOneToManyMapping()) {
            return sql.append(keyWordConverter.select()).append(keyWordConverter.count()).append("(").append("0").append(") ").append(keyWordConverter.from()).append("(")
                    .append(originalSql).append(" ").append(keyWordConverter.group()).append(keyWordConverter.by()).append(JdbcConstant.MAIN_TABLE_NAME)
                    .append(".").append(searchParam.getTableInfo().getId().getDbFieldName())
                    .append(") ").append(JdbcConstant.COUNT_TABLE_NAME).toString();
        } else {
            return sql.append(keyWordConverter.select()).append(keyWordConverter.count()).append("(").append("0").append(") ").append(keyWordConverter.from()).append("(")
                    .append(originalSql)
                    .append(") ").append(JdbcConstant.COUNT_TABLE_NAME).toString();
        }
    }

    @Override
    public String selectSequenceCurrentValueSql(String schema, String sequenceName) {
        if (StringUtil.isNotBlank(schema)) {
            return keyWordConverter.select() + schema + "." + sequenceName + ".currval " + keyWordConverter.from() + "dual";
        }
        return keyWordConverter.select() + sequenceName + ".currval " + keyWordConverter.from() + "dual";
    }

    @Override
    public String selectSequenceNextValueSql(String schema, String sequenceName) {
        if (StringUtil.isNotBlank(schema)) {
            return keyWordConverter.select() + schema + "." + sequenceName + ".nextval " + keyWordConverter.from() + "dual";
        }
        return keyWordConverter.select() + sequenceName + ".nextval " + keyWordConverter.from() + "dual";
    }

    @Override
    public String setSequenceValueSql(String schema, String sequenceName, Object val) {
        return null;
    }
}
