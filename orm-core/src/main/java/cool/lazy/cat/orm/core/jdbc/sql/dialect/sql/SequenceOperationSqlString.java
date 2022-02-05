package cool.lazy.cat.orm.core.jdbc.sql.dialect.sql;

import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/16 11:07
 * 方言中操作序列的sqlString实例
 */
public class SequenceOperationSqlString extends AbstractSqlString implements SqlString, NormalSqlString {

    public SequenceOperationSqlString(String val) {
        super(val);
    }
}
