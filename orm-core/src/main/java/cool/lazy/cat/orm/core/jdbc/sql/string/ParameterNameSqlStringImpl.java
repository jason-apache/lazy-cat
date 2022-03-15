package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.base.constant.Case;

/**
 * @author: mahao
 * @date: 2022-01-25 10:44
 */
public class ParameterNameSqlStringImpl extends AbstractSqlString implements ParameterNameSqlString, NormalSqlString {

    public ParameterNameSqlStringImpl(String val) {
        super(val);
    }

    @Override
    public String getName() {
        return this.getVal();
    }

    @Override
    public void changeCase(Case charCase) {
        ParameterNameSqlString.super.changeCase(charCase);
    }

    @Override
    public String toString() {
        return this.getSymbol() + super.toString();
    }
}
