package com.lazy.cat.orm.core.jdbc.dialect;


import com.lazy.cat.orm.core.jdbc.JdbcConstant;
import com.lazy.cat.orm.core.jdbc.KeyWordConverter;
import com.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 19:22
 */
public class SimpleMysqlDialect implements MysqlDialect {

    @Autowired
    protected KeyWordConverter keyWordConverter;

    @Override
    public String limitSql(SearchParam searchParam, StringBuilder originalSql, List<TableFieldInfoIndexWrapper> indexes) {
        return originalSql.append(" ").append(keyWordConverter.limit()).append(searchParam.getIndex()).append(",").append(searchParam.getPageSize()).toString();
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
        return keyWordConverter.select() + "seq_curval('" + sequenceName + "')";
    }

    @Override
    public String selectSequenceNextValueSql(String schema, String sequenceName) {
        return keyWordConverter.select() + "seq_nextval('" + sequenceName + "')";
    }

    @Override
    public String setSequenceValueSql(String schema, String sequenceName, Object val) {
        return keyWordConverter.call() + "seq_setval('" + sequenceName + "', '" + val.toString() +"')";
    }
}
