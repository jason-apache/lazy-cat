package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/24 12:57
 * 查询基础参数
 */
public interface SearchParam<T> extends Param {

    SearchParam<T> setPojoType(Class<T> pojoType);

    /**
     * pojo类型的表信息
     * @see PojoTableManager#getByPojoType
     * @return 表信息
     */
    TableInfo getTableInfo();

    SearchParam<T> setTableInfo(TableInfo tableInfo);

    /**
     * 添加一个条件
     * @param condition 查询条件
     */
    SearchParam<T> addCondition(SqlCondition condition);

    /**
     * 覆盖查询条件
     * @param condition 查询条件
     */
    SearchParam<T> setCondition(SqlCondition condition);

    /**
     * 供分页使用，数据行起始位置
     * @return 数据行起始位
     */
    int getIndex();

    SearchParam<T> setIndex(int index);

    /**
     * 分页大小
     * @return 分页大小
     */
    int getPageSize();

    SearchParam<T> setPageSize(int pageSize);

    /**
     * 排除指定字段查询
     * @return 字段排除
     */
    Ignorer getIgnorer();

    SearchParam<T> setIgnorer(Ignorer ignorer);

    /**
     * 是否需要分页
     * @return 是否需要分页
     */
    default boolean needPaging() {
        return getPageSize() > 0;
    }

    /**
     * 排序字段
     * @return 排序
     */
    OrderBy getOrderBy();

    SearchParam<T> setOrderBy(OrderBy orderBy);

    /**
     * @return 字段访问器
     */
    FieldAccessor getFieldAccessor();

    SearchParam<T> setFieldAccessor(FieldAccessor fieldAccessor);

    SearchParam<T> setSearchScope(Collection<String> searchScope);

    /**
     * @return 指定容器大小 映射对象的结果集将使用此参数
     */
    default int getContainerSize() {
        return 50;
    }

    SearchParam<T> setContainerSize(int containerSize);
}
