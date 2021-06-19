package cool.lazy.cat.orm.core.jdbc.holder;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/29 20:06
 */
public class UpdateSqlParamHolder implements SqlParamHolder {

    /**
     * 原始sql（where语句）
     */
    private StringBuilder sql;
    /**
     * 条件（where语句中的）
     */
    private Map<String, Object> param;
    private SqlParameterSource paramSource;
    private SqlParameterSource[] paramSources;

    public UpdateSqlParamHolder() {
    }

    public UpdateSqlParamHolder(StringBuilder sql) {
        this.sql = sql;
    }

    public UpdateSqlParamHolder(StringBuilder sql, SqlParameterSource paramSource, SqlParameterSource[] paramSources) {
        this.sql = sql;
        this.paramSource = paramSource;
        this.paramSources = paramSources;
    }

    @Override
    public SqlParameterSource getParamSource() {
        return this.paramSource;
    }

    @Override
    public SqlParameterSource[] getParamSources() {
        return this.paramSources;
    }

    @Override
    public String getSql() {
        return this.sql.toString();
    }

    @Override
    public Map<String, Object> getParam() {
        return param;
    }

    @Override
    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public void setParamSource(SqlParameterSource source) {
        this.paramSource = source;
    }

    @Override
    public void setParamSources(SqlParameterSource[] sources) {
        this.paramSources = sources;
    }
}
