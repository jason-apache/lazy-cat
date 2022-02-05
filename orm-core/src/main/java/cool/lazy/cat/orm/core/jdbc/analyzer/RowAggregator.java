package cool.lazy.cat.orm.core.jdbc.analyzer;

import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 16:24
 * 行聚合器
 */
public interface RowAggregator {

    /**
     * 合并一对多映射，多条数据合并至一个pojo实例中
     *  eg：
     *      当一张表与另一张表存在一对多映射，此时进行join联查，将产生多条数据
     *      需要将多条数据聚合，实际上，聚合器带来的性能影响非常小
     * @param instanceCompound 平铺的实例集合
     * @return 合并后的pojo数据
     */
    List<?> mergeRow(TableInfo tableInfo, FieldAccessor fieldAccessor, List<Object[]> instanceCompound);
}
