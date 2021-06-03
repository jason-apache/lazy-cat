package com.lazy.cat.orm.core.base.bo;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/6 15:35
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -9199256448261053375L;

    private Collection<T> pageContent;
    private int totalCount;

    public Collection<T> getPageContent() {
        return pageContent;
    }

    public PageResult<T> setPageContent(Collection<T> pageContent) {
        this.pageContent = pageContent;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public PageResult<T> setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
