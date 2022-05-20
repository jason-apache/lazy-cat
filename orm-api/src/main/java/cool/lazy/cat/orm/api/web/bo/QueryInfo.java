package cool.lazy.cat.orm.api.web.bo;

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
    private int startIndex;
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
     * @see cool.lazy.cat.orm.api.base.anno.ApiQueryFilter#value()
     */
    private Map<String, Object> params;
    /**
     * 忽略指定字段查询
     */
    private String[] ignoreFields;

    public boolean hasPaging() {
        return pageSize > 0;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getOrderFields() {
        return orderFields;
    }

    public void setOrderFields(String[] orderFields) {
        this.orderFields = orderFields;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String[] getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(String[] ignoreFields) {
        this.ignoreFields = ignoreFields;
    }
}
