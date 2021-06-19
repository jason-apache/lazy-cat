package com.lazy.cat.orm.core.base.repository;

import com.lazy.cat.orm.core.base.FullAutomaticMapping;
import com.lazy.cat.orm.core.base.bo.PageResult;
import com.lazy.cat.orm.core.base.bo.QueryInfo;
import com.lazy.cat.orm.core.base.util.Ignorer;
import com.lazy.cat.orm.core.jdbc.OrderBy;
import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.dto.CascadeLevelMapper;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;
import com.lazy.cat.orm.core.jdbc.param.UpdateParam;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 19:45
 */
public interface BaseRepository<P> extends FullAutomaticMapping {

    /**
     * 返回持有的jdbcTemplate对象
     * @return jdbcTemplate
     */
    NamedParameterJdbcTemplate getJdbcTemplate();

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
     * 此方法为API预留
     * @param queryInfo API查询参数
     * @return 带分页的结果集
     */
    List<P> selectByInfo(QueryInfo queryInfo);

    /**
     * 条件查询单条数据
     * 如果结果集为空，则抛出 EmptyResultDataAccessException异常
     * 如果结果集大于1条，则抛出 IncorrectResultSizeDataAccessException异常
     * @param condition 查询条件，可以为空
     * @return 满足条件的结果集
     */
    default P selectSingle(Condition condition) {
        return this.selectSingle(condition, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 条件查询单条数据
     * 如果结果集为空，则抛出 EmptyResultDataAccessException异常
     * 如果结果集大于1条，则抛出 IncorrectResultSizeDataAccessException异常
     * @param condition 查询条件，可以为空
     * @param ignorer 忽略查询字段
     * @return 满足条件的结果集
     */
    P selectSingle(Condition condition, Ignorer ignorer);

    default <T> T selectSingle(Class<T> pojoType, Condition condition) {
        return this.selectSingle(pojoType, condition, Ignorer.EMPTY_IGNORE);
    }

    <T> T selectSingle(Class<T> pojoType, Condition condition, Ignorer ignorer);

    /**
     * 条件查询
     * @param condition 查询条件，可以为空
     * @return 满足条件的结果集
     */
    default List<P> select(Condition condition) {
        return this.select(condition, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 条件查询
     * @param condition 查询条件，可以为空
     * @param ignorer 忽略查询字段
     * @return 满足条件的结果集
     */
    List<P> select(Condition condition, Ignorer ignorer);

    /**
     * 条件查询
     * @param condition 查询条件，可以为空
     * @param orderBy 排序字段
     * @return 满足条件的结果集
     */
    default List<P> select(Condition condition, OrderBy orderBy) {
        return this.select(condition, orderBy,Ignorer.EMPTY_IGNORE);
    }

    /**
     * 条件查询
     * @param condition 查询条件，可以为空
     * @param orderBy 排序字段
     * @param ignorer 忽略查询字段
     * @return 满足条件的结果集
     */
    List<P> select(Condition condition, OrderBy orderBy, Ignorer ignorer);

    default <T> List<T> select(Class<T> pojoType, Condition condition) {
        return this.select(pojoType, condition, Ignorer.EMPTY_IGNORE);
    }

    <T> List<T> select(Class<T> pojoType, Condition condition, Ignorer ignorer);

    default <T> List<T> select(Class<T> pojoType, Condition condition, OrderBy orderBy) {
        return this.select(pojoType, condition, orderBy, Ignorer.EMPTY_IGNORE);
    }

    <T> List<T> select(Class<T> pojoType, Condition condition, OrderBy orderBy, Ignorer ignorer);

    /**
     * 分页查询，此方法为API预留
     * @param queryInfo API查询参数
     * @return 带分页的结果集
     */
    PageResult<P> selectPage(QueryInfo queryInfo);

    /**
     * 条件分页查询
     * @param condition 查询条件，可以为空
     * @param index 起始行
     * @param pageSize 分页大小
     * @return 带分页的结果集
     */
    default PageResult<P> selectPage(Condition condition, int index, int pageSize) {
        return this.selectPage(condition, index, pageSize, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 条件分页查询
     * @param condition 查询条件，可以为空
     * @param index 起始行
     * @param pageSize 分页大小
     * @param ignorer 忽略查询字段
     * @return 带分页的结果集
     */
    PageResult<P> selectPage(Condition condition, int index, int pageSize, Ignorer ignorer);

    /**
     * 条件分页查询
     * @param condition 查询条件，可以为空
     * @param index 起始行
     * @param orderBy 排序字段
     * @return 带分页的结果集
     */
    default PageResult<P> selectPage(Condition condition, int index, int pageSize, OrderBy orderBy) {
        return this.selectPage(condition, index, pageSize, orderBy, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 条件分页查询
     * @param condition 查询条件，可以为空
     * @param index 起始行
     * @param orderBy 排序字段
     * @param ignorer 忽略查询字段
     * @return 带分页的结果集
     */
    PageResult<P> selectPage(Condition condition, int index, int pageSize, OrderBy orderBy, Ignorer ignorer);

    default <T> PageResult<T> selectPage(Class<T> pojoType, Condition condition, int index, int pageSize) {
        return this.selectPage(pojoType, condition, index, pageSize, Ignorer.EMPTY_IGNORE);
    }

    <T> PageResult<T> selectPage(Class<T> pojoType, Condition condition, int index, int pageSize, Ignorer ignorer);

    default <T> PageResult<T> selectPage(Class<T> pojoType,  Condition condition, int index, int pageSize, OrderBy orderBy) {
        return this.selectPage(pojoType, condition, index, pageSize, orderBy, Ignorer.EMPTY_IGNORE);
    }

    <T> PageResult<T> selectPage(Class<T> pojoType,  Condition condition, int index, int pageSize, OrderBy orderBy, Ignorer ignorer);

    /**
     * 重新定义级联结构查询
     * @param pojoType pojo类型
     * @param cascadeLevel 级联层级（全部级联对象）
     * @param condition 条件查询
     * @param orderBy 排序字段
     * @param <T> 泛型类型
     * @return 结果集
     */
    default <T> List<T> selectByStructure(Class<T> pojoType, int cascadeLevel, Condition condition, OrderBy orderBy) {
        return this.selectByStructure(pojoType, cascadeLevel, condition, orderBy, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 重新定义级联结构查询
     * @param pojoType pojo类型
     * @param cascadeLevel 级联层级（全部级联对象）
     * @param condition 条件查询
     * @param orderBy 排序字段
     * @param ignorer 忽略查询字段
     * @param <T> 泛型类型
     * @return 结果集
     */
    <T> List<T> selectByStructure(Class<T> pojoType, int cascadeLevel, Condition condition, OrderBy orderBy, Ignorer ignorer);

    /**
     * 重新定义级联结构查询
     * @param pojoType pojo类型
     * @param cascadeLevelMapper 级联层级映射
     * @param condition 条件查询
     * @param orderBy 排序字段
     * @param <T> 泛型类型
     * @return 结果集
     */
    default <T> List<T> selectByStructure(Class<T> pojoType, CascadeLevelMapper cascadeLevelMapper, Condition condition, OrderBy orderBy) {
        return this.selectByStructure(pojoType, cascadeLevelMapper, condition, orderBy, Ignorer.EMPTY_IGNORE);
    }

    /**
     * 重新定义级联结构查询
     * @param pojoType pojo类型
     * @param cascadeLevelMapper 级联层级映射
     * @param condition 条件查询
     * @param orderBy 排序字段
     * @param ignorer 忽略查询字段
     * @param <T> 泛型类型
     * @return 结果集
     */
    <T> List<T> selectByStructure(Class<T> pojoType, CascadeLevelMapper cascadeLevelMapper, Condition condition, OrderBy orderBy, Ignorer ignorer);

    /**
     * 新增一条数据
     * @param pojo 数据
     * @return 受影响的行数
     */
    int insert(P pojo);

    /**
     * 新增一条数据，返回数据库自增主键值
     * @param pojo 数据
     * @return 数据库自增主键值
     */
    KeyHolder insert(P pojo, KeyHolder keyHolder);

    /**
     * 批量新增数据，不支持KeyHolder
     * @param pojoList 数据集
     * @return 受影响的行数
     */
    int batchInsert(Collection<P> pojoList);

    /**
     * 修改数据
     * @param pojo 数据
     * @param condition 条件
     * @param ignoreNull 是否忽略更新空值的字段
     * @return 受影响的行数
     */
    default int update(P pojo, Condition condition, boolean ignoreNull) {
        return this.update(pojo, condition, ignoreNull, new String[0]);
    }

    /**
     * 修改数据
     * @param pojo 数据
     * @param condition 条件
     * @param ignoreNull 是否忽略更新空值的字段
     * @param ignoreFields 忽略更新的字段
     * @return 受影响的行数
     */
    int update(P pojo, Condition condition, boolean ignoreNull, String ...ignoreFields);

    /**
     * 根据构建的参数更新数据
     * @param updateParam 修改参数
     * @return 受影响的行数
     */
    int updateByParam(UpdateParam updateParam);

    /**
     * 删除数据
     * @param pojoType 删除数据的类型
     * @param condition 删除条件
     * @param <T> 泛型类型
     * @return 受影响的行数
     */
    <T> int delete(Class<T> pojoType, Condition condition);

    /**
     * 逻辑删除数据
     * 如果对象不存在逻辑删除字段，则抛出FieldDoesNotExistException
     * @param pojo 待删除的数据
     * @param condition 删除条件
     * @return 受影响的行数
     */
    int logicDelete(P pojo, Condition condition);
}
