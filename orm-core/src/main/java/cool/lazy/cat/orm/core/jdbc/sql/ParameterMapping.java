package cool.lazy.cat.orm.core.jdbc.sql;

import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.structure.SqlStructure;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/9/20 21:23
 * 参数映射 包括sql字符串及参数
 */
public interface ParameterMapping {

    void setSqlStructure(SqlStructure sqlStructure);

    /**
     * @return sql结构
     */
    SqlStructure getSqlStructure();

    void setSqlSources(List<SqlSource> sqlSources);

    /**
     * @return sql参数集
     */
    List<SqlSource> getSqlSources();

    /**
     * @return 操作对象类型
     */
    Class<?> getPojoType();

    /**
     * @return 尝试获取slq参数集合中的第一个参数
     */
    default SqlSource getSingleSqlSource() {
        List<SqlSource> sqlSources = this.getSqlSources();
        if (CollectionUtil.isNotEmpty(sqlSources)) {
            return sqlSources.get(0);
        }
        return null;
    }

    void setAffectedFieldMapping(Map<String, PojoField> affectedFields);

    /**
     * @return 受影响的字段
     */
    Map<String, PojoField> getAffectedFieldMapping();
}
