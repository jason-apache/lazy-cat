package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.FromSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.OrderBySqlString;

/**
 * @author: mahao
 * @date: 2021/10/13 14:01
 * 数据查询语言结构
 */
public interface DQLSqlStructure extends SqlStructure {

    /**
     * @return 查询表
     */
    FromSqlString getFrom();

    void setFromSqlString(FromSqlString fromSqlString);

    /**
     * @return 排序描述sql
     */
    OrderBySqlString getOrderBy();

    void setOrderBy(OrderBySqlString orderBy);

}
