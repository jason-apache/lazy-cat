package com.lazy.cat.orm.core.base.bo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/6 15:36
 * API查询参数
 */
public class QueryInfo implements Serializable {

    private static final long serialVersionUID = -8737734564791736766L;
    /**
     * 查询记录起始索引位置
     */
    private int index;
    /**
     * 分页，大于0时开启分页
     */
    private int pageSize = 50;
    /**
     * 排序字段
     */
    private String[] orderFields;
    private boolean asc;
    /**
     * 查询参数，配合queryFilter使用
     * @see com.lazy.cat.orm.core.base.annotation.Column#queryFilter()
     */
    private Map<String, Object> params;
    /**
     * 忽略指定字段查询
     */
    private String[] ignoreFields;

    public boolean hasPaging() {
        return pageSize > 0;
    }

    public int getIndex() {
        return index;
    }

    public QueryInfo setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public QueryInfo setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String[] getOrderFields() {
        return orderFields;
    }

    public QueryInfo setOrderFields(String[] orderFields) {
        this.orderFields = orderFields;
        return this;
    }

    public boolean isAsc() {
        return asc;
    }

    public QueryInfo setAsc(boolean asc) {
        this.asc = asc;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public QueryInfo setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public String[] getIgnoreFields() {
        return ignoreFields;
    }

    public QueryInfo setIgnoreFields(String[] ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }
}
