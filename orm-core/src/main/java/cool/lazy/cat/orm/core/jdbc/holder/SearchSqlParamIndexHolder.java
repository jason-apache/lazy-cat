package cool.lazy.cat.orm.core.jdbc.holder;


import cool.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/20 17:36
 */
public class SearchSqlParamIndexHolder implements SqlParamHolder {

    private SearchSqlParamHolder searchSqlParamHolder;
    /**
     * 列映射关系
     */
    private List<TableFieldInfoIndexWrapper> indexes;
    private ExcludeFieldInfoWrapper excludeFieldInfoWrapper;

    public SearchSqlParamIndexHolder() {
    }

    public SearchSqlParamIndexHolder(SearchSqlParamHolder searchSqlParamHolder, List<TableFieldInfoIndexWrapper> indexes, ExcludeFieldInfoWrapper excludeFieldInfoWrapper) {
        this.searchSqlParamHolder = searchSqlParamHolder;
        this.indexes = indexes;
        this.excludeFieldInfoWrapper = excludeFieldInfoWrapper;
    }

    @Override
    public String getSql() {
        return searchSqlParamHolder.getSql();
    }

    @Override
    public Map<String, Object> getParam() {
        return searchSqlParamHolder.getParam();
    }

    @Override
    public SqlParameterSource getParamSource() {
        return null;
    }

    @Override
    public SqlParameterSource[] getParamSources() {
        return null;
    }

    public TableFieldInfoIndexWrapper[] getIndexesArr() {
        return indexes.toArray(new TableFieldInfoIndexWrapper[0]);
    }

    public ExcludeFieldInfoWrapper getExcludeFieldInfoWrapper() {
        return excludeFieldInfoWrapper;
    }

    @Override
    public void setParam(Map<String, Object> param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setParamSource(SqlParameterSource source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setParamSources(SqlParameterSource[] sources) {
        throw new UnsupportedOperationException();
    }
}
