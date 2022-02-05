package cool.lazy.cat.orm.core.jdbc.sql.interceptor.sql;

import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.CompactSqlStringJoiner;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/7/16 11:07
 * 分页sql载体
 */
public class PageSqlString extends AbstractSqlString implements SqlString, NormalSqlString {

    private boolean useCompact;

    public PageSqlString(String val) {
        super(val);
    }

    public PageSqlString(String val, boolean useCompact) {
        super(val);
        this.useCompact = useCompact;
    }

    @Override
    public SqlStringJoiner joiner() {
        if (useCompact) {
            return new CompactSqlStringJoiner();
        }
        return super.joiner();
    }
}
