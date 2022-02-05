package cool.lazy.cat.orm.core.base.bo;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/6 15:35
 */
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -9199256448261053375L;

    private Collection<T> pageContent;
    private long totalCount;

    public Collection<T> getPageContent() {
        return pageContent;
    }

    public void setPageContent(Collection<T> pageContent) {
        this.pageContent = pageContent;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
