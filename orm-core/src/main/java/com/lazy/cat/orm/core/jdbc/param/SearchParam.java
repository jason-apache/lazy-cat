package com.lazy.cat.orm.core.jdbc.param;


import com.lazy.cat.orm.core.base.util.Ignorer;
import com.lazy.cat.orm.core.jdbc.OrderBy;
import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/24 12:57
 */
public interface SearchParam {

    /**
     * 需要查询的pojo类型
     * @return pojo类型
     */
    Class<?> getPojoType();

    /**
     * pojo类型的表信息
     * 在不清楚表对象的构造时，应调用PojoTableManager
     * @see com.lazy.cat.orm.core.jdbc.manager.PojoTableManager#getByPojoType
     * @return 表信息
     */
    TableInfo getTableInfo();

    /**
     * 查询条件
     * @return 条件
     */
    Condition getCondition();

    /**
     * 查询参数，专供API使用，非API查询请使用Condition查询
     * @return 查询参数
     */
    Map<String, Object> getParams();

    /**
     * 供分页使用，数据行起始位置
     * @return 数据行起始位
     */
    int getIndex();

    /**
     * 分页大小
     * @return 分页大小
     */
    int getPageSize();

    /**
     * 排除指定字段查询
     * @return 字段排除
     */
    Ignorer getIgnorer();

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

    /**
     * 构建的嵌套的表链关系
     * 在不清楚TableChain的构造时，应从tableInfo对象中获取
     * @see TableInfo#getNestedChain()
     * @return 嵌套的链式调用关系
     */
    List<TableChain> getNestedChain();

    /**
     * 构建的平铺的表链关系（由转换嵌套的表链关系得来）
     * 在不清楚TableChain的构造时，应从tableInfo对象中获取
     * @see TableInfo#getFlatChain()
     * @return 平铺的链式调用关系
     */
    List<TableChain> getFlatChain();
}
