package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractCompoundSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/12 19:53
 */
public class ConditionGroupImpl extends AbstractCompoundSqlString<ConditionSqlString> implements ConditionGroup {

    private CombinationType combinationType;
    /**
     * 是否为简单类型的条件组
     */
    private boolean simple;

    public ConditionGroupImpl(CombinationType combinationType) {
        // ConditionGroup仅仅作为条件的容器 不是一个真正需要执行的sql 因此表达式为空白
        super("");
        this.combinationType = combinationType;
    }

    public ConditionGroupImpl(CombinationType combinationType, boolean simple) {
        super("");
        this.combinationType = combinationType;
        this.simple = simple;
    }

    @Override
    public String getOpenOperator() {
        if (this.isSimple()) {
            return "";
        }
        return "(";
    }

    @Override
    public String getCloseOperator() {
        if (this.isSimple()) {
            return "";
        }
        return ")";
    }

    protected boolean isSimple() {
        if (simple) {
            return true;
        }
        if (null != this.getContent()) {
            return this.getContent().size() == 1;
        }
        return false;
    }

    @Override
    public void setCombinationType(CombinationType combinationType) {
        this.combinationType = combinationType;
    }

    @Override
    public CombinationType getCombinationType() {
        return combinationType;
    }

    /**
     * ConditionGroup仅仅作为一组条件的容器 输出sql不应当包括自身 此处覆盖父级方法
     * @return sql
     */
    @Override
    public String toString() {
        Collection<ConditionSqlString> content = this.getContent();
        if (CollectionUtil.isEmpty(content)) {
            return "";
        }
        StringBuilder finalSql = new StringBuilder();
        finalSql.append(this.getOpenOperator());
        SqlString temp = null;
        for (ConditionSqlString conditionSqlString : content) {
            if (null == temp) {
                finalSql.append(conditionSqlString);
            } else {
                finalSql.append(temp.joiner().linkToNext(conditionSqlString));
                finalSql.append(conditionSqlString.joiner().linkToPre(temp)).append(conditionSqlString);
            }
            temp = conditionSqlString;
        }
        return finalSql.append(this.getCloseOperator()).toString();
    }
}
