package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.constant.Case;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/10/11 13:31
 */
public abstract class AbstractCompoundSqlString<S extends SqlString> extends AbstractSqlString implements SqlString, CompoundSqlString<S> {

    protected List<S> content;

    protected AbstractCompoundSqlString(String val) {
        super(val);
    }

    @Override
    public String getOpenOperator() {
        return "";
    }

    @Override
    public String getCloseOperator() {
        return "";
    }

    @Override
    public void combination(S s) {
        if (null == this.content) {
            this.content = new ArrayList<>();
        }
        this.content.add(s);
    }

    @Override
    public Collection<S> getContent() {
        return this.content;
    }

    @Override
    public void changeCase(Case charCase) {
        super.changeCase(charCase);
        if (CollectionUtil.isNotEmpty(this.getContent())) {
            for (S s : this.getContent()) {
                s.changeCase(charCase);
            }
        }
    }

    /**
     * 将自身与开闭符、复合内容全部连接 作为一个整体输出sql
     * @return sql
     */
    @Override
    public String toString() {
        Collection<S> content = this.getContent();
        if (CollectionUtil.isEmpty(content)) {
            return this.getVal();
        }
        StringBuilder finalSql = new StringBuilder();
        // 开启符号
        finalSql.append(super.toString()).append(this.getOpenOperator());
        SqlString temp = this;
        for (S s : content) {
            finalSql.append(temp.joiner().linkToNext(s));
            finalSql.append(s.joiner().linkToPre(temp)).append(s);
            temp = s;
        }
        // 关闭符号
        return finalSql.append(this.getCloseOperator()).toString();
    }
}
