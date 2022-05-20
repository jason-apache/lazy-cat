package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.string.InitializationRequiredSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.ConditionSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/7/16 11:12
 * 表示sql条件的SqlString实例
 */
public interface ConditionSqlString extends SqlString, InitializationRequiredSqlString {

    /**
     * @return 参数名称实例
     */
    String getParameterName();

    /**
     * @return 负载(参数值)
     */
    Object getPayload();

    /**
     * @return 条件类型
     */
    ConditionType getConditionType();

    /**
     * @return 组合关系 none/and/or
     */
    CombinationType getCombinationType();

    /**
     * @param combinationType 组合关系
     */
    void setCombinationType(CombinationType combinationType);

    @Override
    default SqlStringJoiner joiner() {
        return new ConditionSqlStringJoiner(this.getCombinationType());
    }
}
