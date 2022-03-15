package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.base.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.CascadeScopeFieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collection;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/3/23 10:02
 */
public class SearchParamImpl<T> implements SearchParam<T> {

    private Class<T> pojoType;
    private TableInfo tableInfo;
    private SqlCondition condition;
    private int index;
    private int pageSize = -1;
    private Ignorer ignorer;
    private OrderBy orderBy;
    private FieldAccessor fieldAccessor;
    private Set<Class<? extends CommonComponent>> excludeComponents;
    private int containerSize = -1;

    public SearchParamImpl(Class<T> pojoType) {
        this.pojoType = pojoType;
        // 初始化表信息
        this.tableInfo = PojoTableManager.getDefaultInstance().getByPojoType(pojoType).getTableInfo();
    }

    public SearchParamImpl(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        this.pojoType = Caster.cast(tableInfo.getPojoType());
    }

    @Override
    public Class<T> getPojoType() {
        return pojoType;
    }

    @Override
    public SearchParam<T> setPojoType(Class<T> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    public SearchParam<T> setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        return this;
    }

    @Override
    public SqlCondition getCondition() {
        return condition;
    }

    @Override
    public SearchParam<T> addCondition(SqlCondition condition) {
        if (null == this.condition) {
            this.condition = condition;
        } else {
            this.condition.and(condition);
        }
        return this;
    }

    @Override
    public SearchParam<T> setCondition(SqlCondition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public SearchParam<T> setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public SearchParam<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Ignorer getIgnorer() {
        return ignorer;
    }

    @Override
    public SearchParam<T> setIgnorer(Ignorer ignorer) {
        this.ignorer = ignorer;
        return this;
    }

    @Override
    public OrderBy getOrderBy() {
        return orderBy;
    }

    @Override
    public SearchParam<T> setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    @Override
    public FieldAccessor getFieldAccessor() {
        return fieldAccessor;
    }

    @Override
    public SearchParam<T> setFieldAccessor(FieldAccessor fieldAccessor) {
        this.fieldAccessor = fieldAccessor;
        return this;
    }

    @Override
    public SearchParam<T> setSearchScope(Collection<String> searchScope) {
        this.fieldAccessor = new CascadeScopeFieldAccessor(this.tableInfo, searchScope);
        return this;
    }


    @Override
    public Set<Class<? extends CommonComponent>> getExcludeComponents() {
        return this.excludeComponents;
    }

    @Override
    public int getContainerSize() {
        if (containerSize == -1) {
            return 50;
        }
        return containerSize;
    }

    @Override
    public SearchParam<T> setContainerSize(int containerSize) {
        this.containerSize = containerSize;
        return this;
    }
}
