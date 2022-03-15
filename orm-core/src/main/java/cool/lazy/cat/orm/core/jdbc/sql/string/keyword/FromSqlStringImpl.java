package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/10/9 13:06
 */
public class FromSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements FromSqlString {

    private final NameSqlString nameSqlString;

    public FromSqlStringImpl(NameSqlString nameSqlString) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().from());
        this.nameSqlString = nameSqlString;
        this.combination(nameSqlString);
    }

    @Override
    public NameSqlString getObjectNameSql() {
        return nameSqlString;
    }

    @Override
    public boolean paperJam() {
        if (nameSqlString.paperJam()) {
            return true;
        }
        return FromSqlString.super.paperJam();
    }

    @Override
    public Cause cause() {
        if (nameSqlString.paperJam()) {
            return nameSqlString.cause();
        }
        return FromSqlString.super.cause();
    }

    @Override
    public List<JoinSqlString> getJoinSqlStrings() {
        Collection<SqlString> content = this.getContent();
        if (CollectionUtil.isNotEmpty(content)) {
            return Caster.cast(content.stream().filter(s -> s instanceof JoinSqlString).collect(Collectors.toList()));
        }
        return null;
    }
}
