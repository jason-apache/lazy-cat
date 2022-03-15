package cool.lazy.cat.orm.core.jdbc;


import cool.lazy.cat.orm.base.util.CollectionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/24 13:25
 */
public class OrderBy {

    private boolean asc;
    private List<String> fields;

    private OrderBy() {}

    public static OrderBy buildOrderBy(String ...fields) {
        return buildOrderBy(true, fields);
    }

    public static OrderBy buildOrderBy(boolean asc, String ...fields) {
        if (CollectionUtil.isEmpty(fields)) {
            return null;
        }
        return new OrderBy().setAsc(asc).setFields(Arrays.asList(fields));
    }

    public boolean isAsc() {
        return asc;
    }

    public OrderBy setAsc(boolean asc) {
        this.asc = asc;
        return this;
    }

    public List<String> getFields() {
        return fields;
    }

    public OrderBy setFields(List<String> fields) {
        this.fields = fields;
        return this;
    }
}
