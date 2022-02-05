package cool.lazy.cat.orm.api.service;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.api.web.bo.QueryInfo;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-02-05 15:10
 * 通用查询api服务
 */
public interface CommonApiService {

    /**
     * 根据api查询参数查询
     * @param queryInfo api查询参数体
     * @return pojo集合
     */
    List<Object> select(QueryInfo queryInfo);

    /**
     * 根据api查询参数分页查询
     * @param queryInfo api查询参数体
     * @return pojo分页查询结果
     */
    PageResult<Object> selectPage(QueryInfo queryInfo);
}
