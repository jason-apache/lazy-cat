package com.lazy.cat.orm.core.base.repository.impl;


import com.lazy.cat.orm.core.base.AbstractFullAutomaticMapping;
import com.lazy.cat.orm.core.base.bo.PageResult;
import com.lazy.cat.orm.core.base.bo.QueryInfo;
import com.lazy.cat.orm.core.base.repository.BaseRepository;
import com.lazy.cat.orm.core.base.util.Ignorer;
import com.lazy.cat.orm.core.context.FullAutoMappingContext;
import com.lazy.cat.orm.core.jdbc.OrderBy;
import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.holder.SearchSqlParamIndexHolder;
import com.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import com.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;
import com.lazy.cat.orm.core.jdbc.param.SimpleSearchParam;
import com.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import com.lazy.cat.orm.core.jdbc.provider.SqlParamProvider;
import com.lazy.cat.orm.core.manager.PojoManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/5 10:04
 */
public abstract class AbstractRepository<P> extends AbstractFullAutomaticMapping<P> implements BaseRepository<P> {

    @Autowired
    protected PojoManager pojoManager;
    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected SqlParamProvider sqlParamProvider;
    @Autowired
    protected ResultSetExtractorProvider resultSetExtractorProvider;
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public Class<?> getPojoType() {
        Class<?> pojoType = super.getPojoType();
        return pojoType == null ? FullAutoMappingContext.getPojoType() : pojoType;
    }

    protected <T> List<T> queryByInfo(Class<?> pojoType, QueryInfo queryInfo, Ignorer ignorer) {
        return this.query(new SimpleSearchParam(pojoTableManager.getByPojoType(pojoType).getTableInfo()).setPojoType(pojoType)
                .setParams(queryInfo.getParams() == null ? Collections.emptyMap() : queryInfo.getParams())
                .setOrderBy(OrderBy.buildOrderBy(queryInfo.isAsc(), queryInfo.getOrderFields())).setIgnorer(ignorer));
    }

    protected <T> List<T> query(Class<?> pojoType, Condition condition, Ignorer ignorer) {
        return this.query(pojoType, condition, null, ignorer);
    }

    protected <T> List<T> query(Class<?> pojoType, Condition condition, OrderBy orderBy, Ignorer ignorer) {
        return this.query(new SimpleSearchParam(pojoTableManager.getByPojoType(pojoType).getTableInfo()).setPojoType(pojoType)
                .setCondition(condition == null ? Condition.EMPTY_CONDITION : condition).setOrderBy(orderBy).setIgnorer(ignorer));
    }

    @Override
    public <T> List<T> query(SearchParam searchParam) {
        // 构建查询语句
        SearchSqlParamIndexHolder paramIndexHolder = (SearchSqlParamIndexHolder) sqlParamProvider.getSelectSql(searchParam);
        String sql = paramIndexHolder.getSql();
        // 构建列映射关系
        TableChainHolder chainHolder = new TableChainHolder(searchParam.getTableInfo()).setFlatChain(searchParam.getFlatChain()).setNestedChain(searchParam.getNestedChain());
        // 查询、处理对象映射并返回
        return jdbcTemplate.query(sql, paramIndexHolder.getParam(), resultSetExtractorProvider.provider(chainHolder, paramIndexHolder, 1000));
    }

    protected <T> PageResult<T> queryPage(Class<?> pojoType, QueryInfo queryInfo, Ignorer ignorer) {
        return this.queryPage(new SimpleSearchParam(pojoTableManager.getByPojoType(pojoType).getTableInfo()).setPojoType(pojoType)
                .setParams(queryInfo.getParams() == null ? Collections.emptyMap() : queryInfo.getParams()).setIndex(queryInfo.getIndex()).setPageSize(queryInfo.getPageSize())
                .setOrderBy(OrderBy.buildOrderBy(queryInfo.isAsc(), queryInfo.getOrderFields())).setIgnorer(ignorer));
    }

    protected <T> PageResult<T> queryPage(Class<?> pojoType, Condition condition, int index, int pageSize, Ignorer ignorer) {
        return this.queryPage(pojoType, condition, index, pageSize, null, ignorer);
    }

    protected <T> PageResult<T> queryPage(Class<?> pojoType, Condition condition, int index, int pageSize, OrderBy orderBy, Ignorer ignorer) {
        return this.queryPage(new SimpleSearchParam(pojoTableManager.getByPojoType(pojoType).getTableInfo()).setIndex(index).setPojoType(pojoType)
                .setCondition(condition == null ? Condition.EMPTY_CONDITION : condition).setPageSize(pageSize).setOrderBy(orderBy).setIgnorer(ignorer));
    }

    @Override
    public  <T> PageResult<T> queryPage(SearchParam searchParam) {
        PageResult<T> pageResult = new PageResult<>();
        // 构建count查询语句
        SqlParamHolder countSqlHolder = sqlParamProvider.getCountSql(searchParam);
        // 得到count
        Integer totalCount = jdbcTemplate.queryForObject(countSqlHolder.getSql(), countSqlHolder.getParam(), Integer.class);
        // 构建分页查询语句
        SearchSqlParamIndexHolder paramIndexHolder = (SearchSqlParamIndexHolder) sqlParamProvider.getSelectSql(searchParam);
        String sql = paramIndexHolder.getSql();
        pageResult.setTotalCount(totalCount == null ? 0 : totalCount);
        // 构建列映射关系
        TableChainHolder chainHolder = new TableChainHolder(searchParam.getTableInfo()).setFlatChain(searchParam.getFlatChain()).setNestedChain(searchParam.getNestedChain());
        // 查询、处理对象映射并返回
        List<T> content = jdbcTemplate.query(sql, paramIndexHolder.getParam(), resultSetExtractorProvider.provider(chainHolder, paramIndexHolder, pageResult.getTotalCount()));
        pageResult.setPageContent(content);
        return pageResult;
    }
}
