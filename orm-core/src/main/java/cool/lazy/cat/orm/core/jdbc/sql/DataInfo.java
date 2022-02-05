package cool.lazy.cat.orm.core.jdbc.sql;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/25 16:46
 */
public class DataInfo<T> {

    /**
     * 元信息
     */
    private final MetaInfo metaInfo;
    /**
     * 元数据
     */
    private final List<T> metaData;

    public DataInfo(MetaInfo metaInfo, List<T> metaData) {
        this.metaInfo = metaInfo;
        this.metaData = metaData;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public List<T> getMetaData() {
        return metaData;
    }
}
