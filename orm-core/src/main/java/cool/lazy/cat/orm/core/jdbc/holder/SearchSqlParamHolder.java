package cool.lazy.cat.orm.core.jdbc.holder;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/17 15:51
 */
public class SearchSqlParamHolder implements SqlParamHolder {

    private final StringBuilder sql;
    private Map<String, Object> param;

    public SearchSqlParamHolder(StringBuilder sql, Map<String, Object> param) {
        this.sql = sql;
        this.param = param;
    }

    public SearchSqlParamHolder(StringBuilder sql) {
        this.sql = sql;
    }

    @Override
    public String getSql() {
        return this.sql.toString();
    }

    @Override
    public Map<String, Object> getParam() {
        return this.param;
    }

    @Override
    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public SqlParameterSource getParamSource() {
        return null;
    }

    @Override
    public void setParamSource(SqlParameterSource source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlParameterSource[] getParamSources() {
        return null;
    }

    @Override
    public void setParamSources(SqlParameterSource[] sources) {
        throw new UnsupportedOperationException();
    }
}
