package cool.lazy.cat.orm.core.jdbc.sql.source;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;

/**
 * @author: mahao
 * @date: 2021/11/7 17:03
 */
public class LogicDeleteSqlSource extends MapSqlSource implements SqlSource {

    public LogicDeleteSqlSource(Class<?> pojoType, IdField idField, Object id, LogicDeleteField logicDeleteField) {
        super(pojoType);
        super.set(idField.getJavaFieldName(), id);
        super.set(logicDeleteField.getJavaFieldName(), logicDeleteField.getDeleteValue());
    }
}
