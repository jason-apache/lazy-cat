package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;

/**
 * @author: mahao
 * @date: 2021/7/16 11:14
 */
public class JoinConditionSqlStringImpl extends AbstractSqlString implements JoinConditionSqlString {

    private final String originalTable;
    private final String originalTableField;
    private final String joinTable;
    private final String joinTableField;

    public JoinConditionSqlStringImpl(String originalTable, String originalTableField, String joinTable, String joinTableField, String quota) {
        super(originalTable + "." + quota + originalTableField + quota + " = " + joinTable + "." + quota + joinTableField + quota);
        this.originalTable = originalTable;
        this.originalTableField = originalTableField;
        this.joinTable = joinTable;
        this.joinTableField = joinTableField;
    }

    @Override
    public String getOriginalTable() {
        return originalTable;
    }

    @Override
    public String getOriginalTableField() {
        return originalTableField;
    }

    @Override
    public String getJoinTable() {
        return joinTable;
    }

    @Override
    public String getJoinTableField() {
        return joinTableField;
    }
}
