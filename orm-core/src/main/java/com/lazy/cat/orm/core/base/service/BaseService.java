package com.lazy.cat.orm.core.base.service;


import com.lazy.cat.orm.core.base.FullAutomaticMapping;
import com.lazy.cat.orm.core.base.bo.PageResult;
import com.lazy.cat.orm.core.base.bo.QueryInfo;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 19:44
 * 基础服务类，封装基本增删改查方法
 */
public interface BaseService<P> extends FullAutomaticMapping {

    /**
     * 根据构建的查询参数查询
     * @param searchParam 查询参数
     * @return 结果集
     */
    Collection<P> selectByParam(SearchParam searchParam);

    /**
     * 根据构建的查询参数查询，泛型重载
     * @param searchParam 查询参数
     * @return 结果集
     */
    <T> Collection<T> selectByParam(Class<T> pojoType, SearchParam searchParam);

    /**
     * 根据构建的查询参数分页查询
     * @param searchParam 查询参数
     * @return 带分页的结果集
     */
    PageResult<P> selectPageByParam(SearchParam searchParam);

    /**
     * 根据构建的查询参数分页查询，泛型重载
     * @param searchParam 查询参数
     * @return 带分页的结果集
     */
    <T> PageResult<T> selectPageByParam(Class<T> pojoType, SearchParam searchParam);

    /**
     * 根据API查询参数查询
     * @param queryInfo　API查询参数
     * @return 结果集
     */
    Collection<P> select(QueryInfo queryInfo);

    /**
     * 根据API查询参数分页查询
     * @param queryInfo　API查询参数
     * @return 带分页的结果集
     */
    PageResult<P> selectPage(QueryInfo queryInfo);

    /**
     * 根据主键查询
     * @param id 主键
     * @return pojo
     */
    P selectById(Object id);

    /**
     * 根据主键查询，泛型重载
     * @param id 主键
     * @return pojo
     */
    <T> T selectById(Class<T> pojoType, Object id);

    /**
     * 根据主键集合查询
     * @param ids 主键集合
     * @return pojo集合
     */
    Collection<P> selectByIds(List<Object> ids);

    /**
     * 根据主键集合查询，泛型重载
     * @param ids 主键集合
     * @return pojo集合
     */
    <T> Collection<T> selectByIds(Class<T> pojoType, List<Object> ids);

    /**
     * 持久化数据，数据中包含id则修改，否则新增
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo数据
     */
    P save(P pojo, boolean cascade);

    /**
     * 批量持久化数据，数据中包含id则修改，否则新增
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo数据
     */
    Collection<P> batchSave(Collection<P> pojoCollection, boolean cascade);

    /**
     * 新增一条数据
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo数据
     */
    P insert(P pojo, boolean cascade);

    /**
     * 批量新增，调用jdbcTemplate的batchUpdate()
     * @param pojoCollection 数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo集合
     */
    Collection<P> batchInsert(Collection<P> pojoCollection, boolean cascade);

    /**
     * 修改数据默认实现
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo数据
     */
    default P update(P pojo, boolean cascade) {
        return this.update(pojo, cascade, false);
    }

    /**
     * 修改一条数据
     * @param pojo pojo数据
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @param ignoreNull 更新时，是否忽略值为null的属性
     * @return pojo数据
     */
    P update(P pojo, boolean cascade, boolean ignoreNull);

    /**
     * 批量修改数据默认实现
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @return pojo数据集
     */
    default Collection<P> batchUpdate(Collection<P> pojoCollection, boolean cascade) {
        return this.batchUpdate(pojoCollection, cascade, false);
    }

    /**
     * 批量修改数据
     * @param pojoCollection pojo数据集
     * @param cascade 级联操作，置为true则尝试级联持久化对象
     * @param ignoreNull 更新时，是否忽略值为null的属性
     * @return pojo数据集
     */
    Collection<P> batchUpdate(Collection<P> pojoCollection, boolean cascade, boolean ignoreNull);

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
    void batchDelete(Collection<P> pojoCollection, boolean cascade);

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
    void batchLogicDelete(Collection<P> pojoCollection, boolean cascade);

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
    void batchDeleteByInfer(Collection<P> pojoCollection, boolean cascade);

    /**
     * 根据id集合删除数据
     * @param pojoType 指定一个pojo类型
     * @param ids id集合
     */
    <T> void deleteByIds(Class<T> pojoType, Collection<?> ids);

    /**
     * 根据id集合推导删除，如果有逻辑删除字段，则执行逻辑删除，否则物理删除
     * @param pojoType 指定一个pojo类型
     * @param ids id集合
     */
    <T> void deleteByIdsAndInfer(Class<T> pojoType, Collection<?> ids);
}
