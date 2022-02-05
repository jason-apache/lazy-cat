package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.condition.inject.SqlConditionValuePlaceHolder;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.SqlConditionParameterNotMappedCause;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.condition.ConditionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/8/31 10:35
 * 处理sql条件参数注入到sql参数映射器中的修正器
 */
public class SqlConditionParameterNotMappedCorrector implements Corrector {

    @Override
    public boolean support(Class<? extends SqlType> type, Cause cause) {
        return cause instanceof SqlConditionParameterNotMappedCause;
    }

    @Override
    public void fix(Cause c, SqlString sqlString, SqlParameterMapping sqlParameterMapping) {
        SqlConditionParameterNotMappedCause cause = Caster.cast(c);
        ConditionSqlString conditionSqlString = cause.getSqlString();
        if (!(conditionSqlString.getPayload() instanceof SqlConditionValuePlaceHolder)) {
            // 将sql参数放入当前sql操作资源
            for (SqlSource sqlSource : sqlParameterMapping.getCurrentlyProcessed().getSqlSources()) {
                sqlSource.set(conditionSqlString.getParameterName(), conditionSqlString.getPayload());
            }
        }
        conditionSqlString.setInitialization(true);
    }
}
