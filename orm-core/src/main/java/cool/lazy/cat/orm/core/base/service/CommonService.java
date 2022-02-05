package cool.lazy.cat.orm.core.base.service;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/23 14:30
 */
public interface CommonService {

    /**
     * 构造一个指定实体的基础查询参数
     * @param pojoType 实体类型
     * @return 查询参数
     */
    <T> SearchParam<T> buildSearchParam(Class<T> pojoType);

    /**
     * 查询方法泛型重载
     * @param searchParam 查询参数
     * @return 结果集
     */
    <T> List<T> select(SearchParam<T> searchParam);

    /**
     * 查询方法泛型重载
     * @param searchParam 查询参数
     * @return 结果集
     */
    <T> PageResult<T> selectPage(SearchParam<T> searchParam);

    /**
     * 查询单条数据
     * 如果结果集大于1条，则抛出 IncorrectResultSizeDataAccessException异常
     * @param searchParam 查询参数
     * @return 单条数据
     */
    <T> T selectSingleObj(SearchParam<T> searchParam);

    /**
     * 根据主键查询
     * @param id 主键
     * @return pojo
     */
    <T> T selectById(Class<T> pojoType, Object id);

    /**
     * 根据主键集合查询
     * @param ids 主键集合
     * @return pojo集合
     */
    <T> List<T> selectByIds(Class<T> pojoType, Collection<Object> ids);
}
