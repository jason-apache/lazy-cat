package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.service.CommonService;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/23 14:39
 */
public class CommonServiceImpl extends AbstractServiceSupport implements CommonService {

    protected final BaseRepository baseRepository;
    protected final PojoTableManager pojoTableManager;

    public CommonServiceImpl(BaseRepository baseRepository, PojoTableManager pojoTableManager) {
        this.baseRepository = baseRepository;
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public <T> SearchParam<T> buildSearchParam(Class<T> pojoType) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        SearchParamImpl<T> searchParam = new SearchParamImpl<>(tableInfo);
        searchParam.setCondition(this.initCondition(searchParam.getPojoType()));
        return searchParam;
    }

    @Override
    public <T> List<T> select(SearchParam<T> searchParam) {
        return this.getSelectTransactionTemplate(searchParam.getPojoType()).readOnly((s) -> this.baseRepository.query(searchParam));
    }

    @Override
    public <T> PageResult<T> selectPage(SearchParam<T> searchParam) {
        return this.getSelectTransactionTemplate(searchParam.getPojoType()).readOnly((s) -> this.baseRepository.queryPage(searchParam));
    }

    @Override
    public <T> T selectSingleObj(SearchParam<T> searchParam) {
        return this.getSelectTransactionTemplate(searchParam.getPojoType()).readOnly((s) -> this.baseRepository.querySingle(searchParam));
    }

    @Override
    public <T> T selectById(Class<T> pojoType, Object id) {
        return this.getSelectTransactionTemplate(pojoType).readOnly((s) -> this.baseRepository.queryById(pojoType, id));
    }

    @Override
    public <T> List<T> selectByIds(Class<T> pojoType, Collection<Object> ids) {
        return this.getSelectTransactionTemplate(pojoType).readOnly((s) -> this.baseRepository.queryByIds(pojoType, ids));
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
}
