package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.SqlConditionParameterNotMappedCause;
import cool.lazy.cat.orm.core.jdbc.sql.string.AbstractSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;

/**
 * @author: mahao
 * @date: 2021/7/16 12:47
 */
public class ConditionSqlStringImpl extends AbstractSqlString implements ConditionSqlString, SqlString {

    private final ConditionType conditionType;
    private final String parameterName;
    private final Object payload;
    private CombinationType combinationType;
    private final SqlString express;
    private boolean initialized;

    public ConditionSqlStringImpl(String val, ConditionType conditionType, String parameterName, Object payload, CombinationType combinationType, SqlString express) {
        super(val);
        this.conditionType = conditionType;
        this.parameterName = parameterName;
        this.payload = payload;
        this.combinationType = combinationType;
        this.express = express;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    @Override
    public String getParameterName() {
        return parameterName;
    }

    @Override
    public ConditionType getConditionType() {
        return conditionType;
    }

    @Override
    public boolean initialized() {
        return this.initialized;
    }

    @Override
    public void setInitialization(boolean parameterMapped) {
        this.initialized = parameterMapped;
    }

    @Override
    public Cause cause() {
        if (!initialized()) {
            return new SqlConditionParameterNotMappedCause(this);
        }
        return express.cause();
    }

    @Override
    public boolean paperJam() {
        return ConditionSqlString.super.paperJam() || express.paperJam();
    }

    @Override
    public void setCombinationType(CombinationType combinationType) {
        this.combinationType = combinationType;
    }

    @Override
    public CombinationType getCombinationType() {
        return this.combinationType;
    }

    @Override
    public String toString() {
        return super.toString() + this.joiner().linkToNext(express) + express.joiner().linkToPre(this) + express;
    }
}
