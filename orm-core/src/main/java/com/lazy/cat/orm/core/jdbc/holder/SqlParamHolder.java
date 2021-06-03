package com.lazy.cat.orm.core.jdbc.holder;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/4/1 09:58
 */
public interface SqlParamHolder {

    /**
     * 原始sql
     * @return sql
     */
    String getSql();

    Map<String, Object> getParam();

    void setParam(Map<String, Object> param);

    /**
     * jdbcTemplate新增、修改参数
     * @return 单个pojo对象参数
     */
    SqlParameterSource getParamSource();

    void setParamSource(SqlParameterSource source);

    /**
     * jdbcTemplate新增、修改参数
     * @return 多个pojo对象参数
     */
    SqlParameterSource[] getParamSources();

    void setParamSources(SqlParameterSource[] sources);
}
