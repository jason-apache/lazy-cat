package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;

/**
 * @author: mahao
 * @date: 2021/3/23 10:44
 * 参数注入器，增删改注入额外的sql，返回一个参数对象
 * 该对象可能是一个SqlParameterSource实现类，也可能是一个map
 */
public interface ParameterInjector {

    /**
     * 查询时注入sql，返回条件参数
     * @param searchParam 查询参数
     * @param sql 原始sql
     * @return 条件参数
     */
    Object injectOfSelect(SearchParam searchParam, StringBuilder sql);

    /**
     * 给数据集注入id
     * @param id id策略
     * @param data 数据集
     */
    void injectId(IdStrategy id, Object data);

    /**
     * 新增时生成SqlParameterSource，注入id并注入typeConverter转换参数
     * @param updateParam 参数条件
     * @param sql 原始sql
     * @return SqlParameterSource
     */
    Object injectOfInsert(UpdateParam updateParam, StringBuilder sql);

    /**
     * 删除时注入sql，将条件注入map中并返回
     * @param updateParam 参数条件
     * @param sql 原始sql
     * @return 删除条件持有者
     */
    Object injectOfDelete(UpdateParam updateParam, StringBuilder sql);

    /**
     * 逻辑删除时注入sql，生成SqlParameterSource
     * @param updateParam 参数条件
     * @param sql 原始sql
     * @return SqlParameterSource
     */
    Object injectOfLogicDelete(UpdateParam updateParam, StringBuilder sql);

    /**
     * 修改时注入sql，生成SqlParameterSource、注入typeConverter参数
     * @param updateParam 参数条件
     * @param sql 原始sql
     * @return SqlParameterSource
     */
    Object injectOfUpdate(UpdateParam updateParam, StringBuilder sql);
}
