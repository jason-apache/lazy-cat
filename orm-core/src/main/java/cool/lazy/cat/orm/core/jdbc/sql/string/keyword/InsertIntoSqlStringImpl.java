package cool.lazy.cat.orm.core.jdbc.sql.string.keyword;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.joiner.SqlStringJoiner;

/**
 * @author: mahao
 * @date: 2021/10/9 13:10
 */
public class InsertIntoSqlStringImpl extends AbstractCompoundSqlString<SqlString> implements InsertIntoSqlString {

    private final NameSqlString nameSqlString;

    public InsertIntoSqlStringImpl(NameSqlString nameSqlString) {
        super(JdbcOperationSupport.getDialect().getKeywordDictionary().insert() + " " + JdbcOperationSupport.getDialect().getKeywordDictionary().into());
        this.nameSqlString = nameSqlString;
    }

    @Override
    public String getOpenOperator() {
        return "(";
    }

    @Override
    public String getCloseOperator() {
        return ")";
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
        return InsertIntoSqlString.super.paperJam();
    }

    @Override
    public Cause cause() {
        if (nameSqlString.paperJam()) {
            return nameSqlString.cause();
        }
        return InsertIntoSqlString.super.cause();
    }

    @Override
    public String toString() {
        if (CollectionUtil.isEmpty(content)) {
            return this.getVal() + " " + this.nameSqlString.toString();
        }
        SqlStringJoiner joiner = nameSqlString.joiner();
        StringBuilder finalSql = new StringBuilder();
        finalSql.append(super.getVal()).append(this.joiner().linkToNext(nameSqlString))
                .append(joiner.linkToPre(this)).append(nameSqlString).append(this.getOpenOperator());
        SqlString temp = nameSqlString;
        for (SqlString s : content) {
            finalSql.append(temp.joiner().linkToNext(s));
            finalSql.append(s.joiner().linkToPre(temp)).append(s);
            temp = s;
        }
        return finalSql.append(this.getCloseOperator()).toString();
    }
}
