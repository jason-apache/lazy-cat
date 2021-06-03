package com.lazy.cat.orm.core.jdbc.analyzer;


import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import com.lazy.cat.orm.core.jdbc.param.SearchParam;

/**
 * @author: mahao
 * @date: 2021/3/13 20:47
 * 条件解析器
 */
public interface ConditionAnalyzer {

    /**
     * 解析查询参数，条件可能使用Condition，也可能使用map
     * @param searchParam 查询参数
     * @return 解析结果
     */
    SqlParamHolder analysis(SearchParam searchParam);

    /**
     * 解析修改、删除参数
     * @param pojoType pojo类型
     * @param condition 条件
     * @return 解析结果
     */
    SqlParamHolder analysis(Class<?> pojoType, Condition condition);
}
