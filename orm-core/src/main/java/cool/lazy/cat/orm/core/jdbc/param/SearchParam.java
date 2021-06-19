package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/24 12:57
 */
public interface SearchParam<T> {

    /**
     * 需要查询的pojo类型
     * @return pojo类型
     */
    Class<T> getPojoType();

    SearchParam<T> setPojoType(Class<T> pojoType);

    /**
     * pojo类型的表信息
     * 在不清楚表对象的构造时，应调用PojoTableManager
     * @see PojoTableManager#getByPojoType
     * @return 表信息
     */
    TableInfo getTableInfo();

    SearchParam<T> setTableInfo(TableInfo tableInfo);

    /**
     * 查询条件
     * @return 条件
     */
    Condition getCondition();

    SearchParam<T> setCondition(Condition condition);

    /**
     * 查询参数，专供API使用，非API查询请使用Condition查询
     * @return 查询参数
     */
    Map<String, Object> getParams();

    SearchParam<T> setParams(Map<String, Object> params);

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
    boolean needPaging();

    /**
     * 排序字段
     * @return 排序
     */
    OrderBy getOrderBy();

    SearchParam<T> setOrderBy(OrderBy orderBy);

    /**
     * 构建的嵌套的表链关系
     * 在不清楚TableChain的构造时，应从tableInfo对象中获取
     * @see TableInfo#getNestedChain()
     * @return 嵌套的链式调用关系
     */
    List<TableChain> getNestedChain();

    SearchParam<T> setNestedChain(List<TableChain> nestedChain);

    /**
     * 构建的平铺的表链关系（由转换嵌套的表链关系得来）
     * 在不清楚TableChain的构造时，应从tableInfo对象中获取
     * @see TableInfo#getFlatChain()
     * @return 平铺的链式调用关系
     */
    List<TableChain> getFlatChain();

    SearchParam<T> setFlatChain(List<TableChain> flatChain);
}
