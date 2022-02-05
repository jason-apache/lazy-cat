package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/3/13 20:47
 * 条件解析器
 */
public interface ConditionAnalyzer {

    /**
     * 解析查询条件
     * @param sqlCondition 查询参数
     * @return 解析结果
     */
    WhereSqlString<?> analysis(Class<? extends SqlType> type, Class<?> pojoType, FieldAccessor fieldAccessor, SqlCondition sqlCondition);
}
