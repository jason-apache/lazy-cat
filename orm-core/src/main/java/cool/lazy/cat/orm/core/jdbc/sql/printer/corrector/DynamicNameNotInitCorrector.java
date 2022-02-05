package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.adapter.DynamicNameAdapter;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.DynamicNameNotInitCause;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/7/28 17:07
 * 初始化动态名称
 */
public class DynamicNameNotInitCorrector implements Corrector {

    protected final DynamicNameAdapter dynamicNameAdapter;

    public DynamicNameNotInitCorrector(DynamicNameAdapter dynamicNameAdapter) {
        this.dynamicNameAdapter = dynamicNameAdapter;
    }

    @Override
    public boolean support(Class<? extends SqlType> type, Cause cause) {
        return cause instanceof DynamicNameNotInitCause;
    }

    @Override
    public void fix(Cause ref, SqlString sqlString, SqlParameterMapping sqlParameterMapping) {
        DynamicNameNotInitCause cause = Caster.cast(ref);
        DynamicNameSqlString dynamicNameSqlString = cause.getSqlString();
        ObjectName objectName = dynamicNameAdapter.adapt(sqlParameterMapping.getType(), dynamicNameSqlString.getPojoType(), dynamicNameSqlString.getSchema(), dynamicNameSqlString.getName());
        // 初始化动态名称
        dynamicNameSqlString.setSchema(objectName.getSchema());
        dynamicNameSqlString.setName(objectName.getName());
        dynamicNameSqlString.setInitialization(true);
    }
}
