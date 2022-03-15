package cool.lazy.cat.orm.core.jdbc.param;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

import java.util.Collections;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/7/27 18:01
 */
public class DeleteParamImpl implements DeleteParam {

    private final Class<?> pojoType;
    private final Object data;
    private SqlCondition condition;
    private boolean cascade;

    public DeleteParamImpl(Object data) {
        this.data = data;
        this.cascade = true;
        this.pojoType = Caster.cast(CollectionUtil.getTypeFromObj(data));
    }

    public DeleteParamImpl(Object data, boolean cascade) {
        this(data);
        this.cascade = cascade;
    }

    public DeleteParamImpl(Class<?> pojoType, SqlCondition condition) {
        this.data = null;
        this.cascade = false;
        this.pojoType = pojoType;
        this.condition = condition;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public SqlCondition getCondition() {
        return condition;
    }

    @Override
    public DeleteParam setCondition(SqlCondition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Set<Class<? extends CommonComponent>> getExcludeComponents() {
        return Collections.emptySet();
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public boolean cascade() {
        return cascade;
    }
}
