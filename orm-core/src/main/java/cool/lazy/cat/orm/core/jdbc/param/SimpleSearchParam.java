package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/23 10:02
 */
public class SimpleSearchParam<T> implements SearchParam<T> {

    private Class<T> pojoType;
    private TableInfo tableInfo;
    private Condition condition;
    private Map<String, Object> params;
    private int index;
    private int pageSize = -1;
    private Ignorer ignorer;
    private OrderBy orderBy;
    private List<TableChain> nestedChain;
    private List<TableChain> flatChain;

    public SimpleSearchParam(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public SimpleSearchParam(Class<T> pojoType, TableInfo tableInfo, Condition condition, int index, int pageSize, Ignorer ignorer, OrderBy orderBy, List<TableChain> nestedChain, List<TableChain> flatChain) {
        this.pojoType = pojoType;
        this.tableInfo = tableInfo;
        this.condition = condition;
        this.index = index;
        this.pageSize = pageSize;
        this.ignorer = ignorer;
        this.orderBy = orderBy;
        this.nestedChain = nestedChain;
        this.flatChain = flatChain;
    }

    public SimpleSearchParam(Class<T> pojoType, TableInfo tableInfo, Map<String, Object> params, int index, int pageSize, Ignorer ignorer, OrderBy orderBy, List<TableChain> nestedChain, List<TableChain> flatChain) {
        this.pojoType = pojoType;
        this.tableInfo = tableInfo;
        this.params = params;
        this.index = index;
        this.pageSize = pageSize;
        this.ignorer = ignorer;
        this.orderBy = orderBy;
        this.nestedChain = nestedChain;
        this.flatChain = flatChain;
    }

    @Override
    public boolean needPaging() {
        return pageSize != -1;
    }

    @Override
    public Class<T> getPojoType() {
        return pojoType;
    }

    @Override
    public SimpleSearchParam<T> setPojoType(Class<T> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    @Override
    public SimpleSearchParam<T> setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        return this;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public SimpleSearchParam<T> setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    @Override
    public SimpleSearchParam<T> setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public SimpleSearchParam<T> setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public SimpleSearchParam<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Ignorer getIgnorer() {
        return ignorer;
    }

    @Override
    public SimpleSearchParam<T> setIgnorer(Ignorer ignorer) {
        this.ignorer = ignorer;
        return this;
    }

    @Override
    public OrderBy getOrderBy() {
        return orderBy;
    }

    @Override
    public SimpleSearchParam<T> setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    @Override
    public List<TableChain> getNestedChain() {
        if (nestedChain == null) {
            return tableInfo.getNestedChain();
        }
        return nestedChain;
    }

    @Override
    public SimpleSearchParam<T> setNestedChain(List<TableChain> nestedChain) {
        this.nestedChain = nestedChain;
        return this;
    }

    @Override
    public List<TableChain> getFlatChain() {
        if (flatChain == null) {
            return tableInfo.getFlatChain();
        }
        return flatChain;
    }

    @Override
    public SimpleSearchParam<T> setFlatChain(List<TableChain> flatChain) {
        this.flatChain = flatChain;
        return this;
    }
}
