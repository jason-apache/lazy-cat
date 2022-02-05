package cool.lazy.cat.orm.api.service.impl;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.service.CommonApiService;
import cool.lazy.cat.orm.api.util.ConditionHelper;
import cool.lazy.cat.orm.api.web.FullAutoMappingContext;
import cool.lazy.cat.orm.api.web.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.service.impl.AbstractServiceSupport;
import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-02-05 15:10
 */
public class CommonApiServiceImpl extends AbstractServiceSupport implements CommonApiService {

    protected final BaseRepository baseRepository;
    protected final PojoTableManager pojoTableManager;
    protected final ApiPojoManager apiPojoManager;

    public CommonApiServiceImpl(BaseRepository baseRepository, PojoTableManager pojoTableManager, ApiPojoManager apiPojoManager) {
        this.baseRepository = baseRepository;
        this.pojoTableManager = pojoTableManager;
        this.apiPojoManager = apiPojoManager;
    }

    @Override
    public List<Object> select(QueryInfo queryInfo) {
        return this.getSelectTransactionTemplate(FullAutoMappingContext.getPojoType()).readOnly((s) -> this.baseRepository.query(this.convertToParam(queryInfo)));
    }

    @Override
    public PageResult<Object> selectPage(QueryInfo queryInfo) {
        return this.getSelectTransactionTemplate(FullAutoMappingContext.getPojoType()).readOnly((s) -> this.baseRepository.queryPage(this.convertToParam(queryInfo)));
    }

    /**
     * 初始化默认查询条件
     * 如果pojo存在逻辑删除字段，则添加逻辑删除字段控制
     * @param pojoType pojo类型
     * @return 默认条件
     */
    protected Condition initCondition(Class<?> pojoType) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        if (null != tableInfo.getLogicDeleteField()) {
            LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
            return Condition.eq(logicDeleteField.getJavaFieldName(), logicDeleteField.getNormalValue());
        }
        return null;
    }

    /**
     * 将api查询参数体转换为通用查询参数
     * @param queryInfo api查询参数体
     * @return 通用查询参数
     */
    protected SearchParam<Object> convertToParam(QueryInfo queryInfo) {
        Class<?> pojoType = FullAutoMappingContext.getPojoType();
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        SearchParamImpl<Object> searchParam = new SearchParamImpl<>(tableInfo);
        searchParam.setIndex(queryInfo.getStartIndex());
        searchParam.setPageSize(queryInfo.getPageSize());
        searchParam.setOrderBy(OrderBy.buildOrderBy(queryInfo.isAsc(), queryInfo.getOrderFields()));
        searchParam.setIgnorer(Ignorer.build(queryInfo.getIgnoreFields()));
        Condition condition = this.initCondition(pojoType);
        SqlCondition sqlCondition = ConditionHelper.convert(pojoType, apiPojoManager, queryInfo.getParams());
        if (null != condition) {
            searchParam.setCondition(condition.and(sqlCondition));
        } else {
            searchParam.setCondition(sqlCondition);
        }
        return searchParam;
    }
}
