package com.lazy.cat.orm.core.jdbc;


import com.lazy.cat.orm.core.base.util.CollectionUtil;

/**
 * @author: mahao
 * @date: 2021/3/24 13:25
 */
public class OrderBy {

    private boolean asc;
    private String[] fields;

    private OrderBy() {}

    public static OrderBy buildOrderBy(boolean asc, String ...fields) {
        if (CollectionUtil.isEmpty(fields)) {
            return null;
        }
        return new OrderBy().setAsc(asc).setFields(fields);
    }

    public boolean isAsc() {
        return asc;
    }

    public OrderBy setAsc(boolean asc) {
        this.asc = asc;
        return this;
    }

    public String[] getFields() {
        return fields;
    }

    public OrderBy setFields(String[] fields) {
        this.fields = fields;
        return this;
    }
}
