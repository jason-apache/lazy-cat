package cool.lazy.cat.orm.core.base.repository;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.jdbc.param.DeleteParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 19:45
 */
public interface BaseRepository {

    default <T> List<T> query(Class<T> pojoType) {
        return this.query(new SearchParamImpl<>(pojoType));
    }

    /**
     * 根据构建的查询参数查询
     * @param searchParam 查询参数
     * @param <T> 泛型类型
     * @return 结果集
     */
    <T> List<T> query(SearchParam<T> searchParam);

    /**
     * 根据构建的查询参数分页查询
     * @param searchParam 查询参数
     * @param <T> 泛型类型
     * @return 带分页的结果集
     */
    <T> PageResult<T> queryPage(SearchParam<T> searchParam);

    /**
     * 查询单个对象
     * @param searchParam 查询参数
     * @param <T> 泛型类型
     * @return 单个对象 可能为null
     */
    default <T> T querySingle(SearchParam<T> searchParam) {
        return DataAccessUtils.singleResult(this.query(searchParam));
    }

    /**
     * 根据主键查询
     * @param type pojo类型
     * @param id id
     * @param <T> 泛型类型
     * @return pojo
     */
    <T> T queryById(Class<T> type, Object id);

    /**
     * 根据主键集合查询 参数可以是数组、集合、基本类型对象、charSequence
     * @param type pojo类型
     * @param ids ids
     * @param <T> 泛型类型
     * @return pojo集合
     */
    <T> List<T> queryByIds(Class<T> type, Object ids);

    /**
     * 更新
     * @param updateParam 修改参数
     */
    void update(UpdateParam updateParam);

    default void update(Object data) {
        this.update(data, true, false);
    }

    /**
     * 根据id更新
     * @param data pojo对象 可以是单个对象、集合对象、数组
     * @param ignoreNull 是否忽略参数为null的字段更新数据库
     * @param cascade 是否同步更新关联对象 如果同步更新 则会将关联条件的字段重新赋值
     */
    void update(Object data, boolean cascade, boolean ignoreNull);

    /**
     * 根据条件更新
     * @param sqlSource 参数
     * @param sqlCondition 条件
     */
    void updateByCondition(SqlSource sqlSource, SqlCondition sqlCondition);

    default void save(Object data) {
        this.save(data, true);
    }

    /**
     * 保存 id不为空则更新 否则新增
     * @param data pojo对象 可以是单个对象、集合对象、数组
     * @param cascade 是否同步更新关联对象 如果同步更新 则会将关联条件的字段重新赋值
     */
    void save(Object data, boolean cascade);

    /**
     * 删除
     * @param deleteParam 删除参数
     */
    void delete(DeleteParam deleteParam);

    default void delete(Object data) {
        delete(data, true);
    }

    /**
     * 根据id删除
     * @param data pojo对象 可以是单个对象、集合对象、数组
     * @param cascade 是否同步删除关联对象
     */
    void delete(Object data, boolean cascade);

    /**
     * 根据id删除
     * @param pojoType pojo类型
     * @param id id
     */
    void deleteById(Class<?> pojoType, Object id);

    /**
     * 根据id集合删除
     * @param pojoType pojo类型
     * @param ids ids 可以是对象、集合对象、数组
     */
    void deleteByIds(Class<?> pojoType, Object ids);

    /**
     * 根据条件删除
     * @param pojoType pojo类型
     * @param sqlCondition 删除条件
     */
    void deleteByCondition(Class<?> pojoType, SqlCondition sqlCondition);
    
    default void logicDelete(Object data) {
        this.logicDelete(data, true);
    }

    /**
     * 根据id逻辑删除
     * @param data pojo对象 可以是单个对象、集合对象、数组
     * @param cascade 是否同步删除关联对象
     */
    void logicDelete(Object data, boolean cascade);

    /**
     * 根据id逻辑删除
     * @param pojoType pojo类型
     * @param id id
     */
    void logicDeleteById(Class<?> pojoType, Object id);

    /**
     * 根据id集合逻辑删除
     * @param pojoType pojo类型
     * @param ids id集合 可以是集合、数组、对象
     */
    void logicDeleteByIds(Class<?> pojoType, Object ids);

    /**
     * 根据条件逻辑删除
     * @param pojoType pojo类型
     * @param sqlCondition 删除条件
     */
    void logicDeleteByCondition(Class<?> pojoType, SqlCondition sqlCondition);

    /**
     * 根据id推导删除
     * 如果对象存在逻辑删除字段 则逻辑删除 否则物理删除
     * @param data pojo对象 可以是单个对象、集合对象、数组
     * @param cascade 是否同步删除关联对象
     */
    void deleteByInfer(Object data, boolean cascade);

    /**
     * 根据ids推导删除
     * 如果对象存在逻辑删除字段 则逻辑删除 否则物理删除
     * @param pojoType pojo类型
     * @param ids id集合
     */
    void deleteByIdsAndInfer(Class<?> pojoType, Object ids);
}
