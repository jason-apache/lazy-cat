package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.JoinConditionSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/10/13 14:57
 * 表示连接条件的sqlString实例
 */
public interface JoinConditionSqlString extends SqlString, NormalSqlString {

    /**
     * @return 源表名
     */
    String getOriginalTable();

    /**
     * @return 源表字段名
     */
    String getOriginalTableField();

    /**
     * @return 关联查询的表名
     */
    String getJoinTable();

    /**
     * @return 关联查询的表字段
     */
    String getJoinTableField();

    @Override
    default SqlStringJoiner joiner() {
        return new JoinConditionSqlStringJoiner();
    }
}
