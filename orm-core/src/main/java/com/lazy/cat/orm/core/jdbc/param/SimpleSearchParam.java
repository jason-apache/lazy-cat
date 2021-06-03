package com.lazy.cat.orm.core.jdbc.param;


import com.lazy.cat.orm.core.base.util.Ignorer;
import com.lazy.cat.orm.core.jdbc.OrderBy;
import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/23 10:02
 */
public class SimpleSearchParam implements SearchParam {

    private Class<?> pojoType;
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

    public SimpleSearchParam(Class<?> pojoType, TableInfo tableInfo, Condition condition, int index, int pageSize, Ignorer ignorer, OrderBy orderBy, List<TableChain> nestedChain, List<TableChain> flatChain) {
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

    public SimpleSearchParam(Class<?> pojoType, TableInfo tableInfo, Map<String, Object> params, int index, int pageSize, Ignorer ignorer, OrderBy orderBy, List<TableChain> nestedChain, List<TableChain> flatChain) {
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
    public Class<?> getPojoType() {
        return pojoType;
    }

    public SimpleSearchParam setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    @Override
    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public SimpleSearchParam setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        return this;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    public SimpleSearchParam setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }

    public SimpleSearchParam setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public SimpleSearchParam setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public SimpleSearchParam setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Ignorer getIgnorer() {
        return ignorer;
    }

    public SimpleSearchParam setIgnorer(Ignorer ignorer) {
        this.ignorer = ignorer;
        return this;
    }

    @Override
    public OrderBy getOrderBy() {
        return orderBy;
    }

    public SimpleSearchParam setOrderBy(OrderBy orderBy) {
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

    public SimpleSearchParam setNestedChain(List<TableChain> nestedChain) {
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

    public SimpleSearchParam setFlatChain(List<TableChain> flatChain) {
        this.flatChain = flatChain;
        return this;
    }
}
