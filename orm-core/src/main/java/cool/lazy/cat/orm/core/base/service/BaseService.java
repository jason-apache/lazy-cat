package cool.lazy.cat.orm.core.base.service;


import cool.lazy.cat.orm.core.base.FullAutomaticMapping;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 19:44
 * 基础服务类，封装基本增删改查方法
 */
public interface BaseService<P> extends FullAutomaticMapping {

    /**
     * 构建pojo查询参数
     * @return pojo查询参数
     */
    SearchParam<P> buildSearchParam();

    /**
     * 根据参数查询
     * @param searchParam 查询参数
     * @return pojo集合
     */
    List<P> select(SearchParam<P> searchParam);

    /**
     * 根据参数分页查询
     * @param searchParam 查询参数
     * @return pojo分页查询结果
     */
    PageResult<P> selectPage(SearchParam<P> searchParam);

    /**
     * 根据主键查询
     * @param id id
     * @return pojo
     */
    P selectById(Object id);

    /**
     * 根据主键id查询
     * @param ids id集合
     * @return pojo集合
     */
    List<P> selectByIds(Collection<Object> ids);

    /**
     * 持久化数据，数据中包含id则修改，否则新增
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     * @return pojo数据
     */
    P save(P pojo, boolean cascade);

    /**
     * 批量持久化数据，数据中包含id则修改，否则新增
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     */
    void save(Collection<P> pojoCollection, boolean cascade);

    /**
     * 修改数据默认实现
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     * @return pojo数据
     */
    default P update(P pojo, boolean cascade) {
        return this.update(pojo, cascade, false);
    }

    /**
     * 修改一条数据
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     * @param ignoreNull 更新时，是否忽略值为null的属性
     * @return pojo数据
     */
    P update(P pojo, boolean cascade, boolean ignoreNull);

    /**
     * 批量修改数据默认实现
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     */
    default void update(Collection<P> pojoCollection, boolean cascade) {
        this.update(pojoCollection, cascade, false);
    }

    /**
     * 批量修改数据
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象 并将关联条件的字段重新赋值
     * @param ignoreNull 更新时，是否忽略值为null的属性
     */
    void update(Collection<P> pojoCollection, boolean cascade, boolean ignoreNull);

    /**
     * 删除一条数据
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void delete(P pojo, boolean cascade);

    /**
     * 批量删除
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void delete(Collection<P> pojoCollection, boolean cascade);

    /**
     * 逻辑删除
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void logicDelete(P pojo, boolean cascade);

    /**
     * 批量逻辑删除
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void logicDelete(Collection<P> pojoCollection, boolean cascade);

    /**
     * 根据id集合逻辑删除数据
     * @param pojoType pojo类型
     * @param ids id集合
     */
    void logicDeleteByIds(Class<?> pojoType, Collection<?> ids);

    /**
     * 推导删除，如果有逻辑删除字段，则执行逻辑删除，否则物理删除
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void deleteByInfer(P pojo, boolean cascade);

    /**
     * 批量推导删除，如果有逻辑删除字段，则执行逻辑删除，否则物理删除
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     */
    void deleteByInfer(Collection<P> pojoCollection, boolean cascade);

    /**
     * 根据id集合删除数据
     * @param pojoType 指定一个pojo类型
     * @param ids id集合
     */
    void deleteByIds(Class<?> pojoType, Collection<?> ids);

    /**
     * 根据id集合推导删除，如果有逻辑删除字段，则执行逻辑删除，否则物理删除
     * @param pojoType 指定一个pojo类型
     * @param ids id集合
     */
    void deleteByIdsAndInfer(Class<?> pojoType, Collection<?> ids);
}
