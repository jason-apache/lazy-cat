package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.NormalSymbolSqlString;

/**
 * @author: mahao
 * @date: 2021/7/20 11:58
 * 一个正常的、不会卡纸的条件sqlString实例
 */
public class NormalConditionSqlString extends NormalSymbolSqlString implements ConditionSqlString, NormalSqlString {

    public NormalConditionSqlString(String val) {
        super(val);
    }

    @Override
    public boolean paperJam() {
        return super.paperJam();
    }

    @Override
    public Cause cause() {
        return null;
    }

    @Override
    public String getParameterName() {
        return null;
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public ConditionType getConditionType() {
        return null;
    }

    @Override
    public boolean initialized() {
        return true;
    }

    @Override
    public void setInitialization(boolean parameterMapped) {
    }

    @Override
    public CombinationType getCombinationType() {
        return CombinationType.NONE;
    }

    @Override
    public void setCombinationType(CombinationType combinationType) {
    }
}
