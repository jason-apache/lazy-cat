package cool.lazy.cat.orm.core.jdbc.provider;


import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;

/**
 * @author: mahao
 * @date: 2021/3/10 14:49
 * 增删改sql参数提供者
 */
public interface SqlParamProvider {

    /**
     * 获取条件查询sql参数
     * @param searchParam 查询条件
     * @return sql参数
     */
    SqlParamHolder getSelectSql(SearchParam searchParam);

    /**
     * count查询sql参数
     * @param searchParam 查询条件
     * @return sql参数
     */
    SqlParamHolder getCountSql(SearchParam searchParam);

    /**
     * 提供新增、批量新增的sql参数
     * @param updateParam 修改数据参数
     * @return sql参数
     */
    SqlParamHolder getInsertSql(UpdateParam updateParam);

    /**
     * 提供修改的sql参数
     * @param updateParam 修改数据参数
     * @return sql参数
     */
    SqlParamHolder getUpdateSql(UpdateParam updateParam);

    /**
     * 提供删除的sql参数
     * @param updateParam 修改数据参数
     * @return sql参数
     */
    SqlParamHolder getDeleteSql(UpdateParam updateParam);

    /**
     * 提供逻辑删除的sql参数
     * @param updateParam 修改数据参数
     * @return sql参数
     */
    SqlParamHolder getLogicDeleteSql(UpdateParam updateParam);
}
