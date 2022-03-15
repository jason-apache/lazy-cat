package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/4/14 10:53
 */
public class UpdateParamImpl implements UpdateParam {

    private final Object data;
    private Class<?> pojoType;
    private final boolean cascade;
    private boolean ignoreNull;
    private SqlCondition sqlCondition;
    private Set<Class<? extends CommonComponent>> excludeComponents;
    private Set<String> updateFields;

    public UpdateParamImpl(Object data) {
        this.data = data;
        this.cascade = true;
        this.pojoType = Caster.cast(CollectionUtil.getTypeFromObj(data));
    }

    public UpdateParamImpl(Object data, boolean cascade) {
        this.data = data;
        this.pojoType = Caster.cast(CollectionUtil.getTypeFromObj(data));
        this.cascade = cascade;
    }

    public UpdateParamImpl(SqlSource sqlSource, SqlCondition sqlCondition) {
        this.pojoType = sqlSource.getPojoType();
        this.data = sqlSource;
        this.sqlCondition = sqlCondition;
        this.cascade = false;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public void setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    @Override
    public boolean ignoreNullField() {
        return ignoreNull;
    }

    public UpdateParam setIgnoreNull(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
        return this;
    }

    @Override
    public Object getData() {
        return data;
    }

    public UpdateParam excludeComponent(Class<? extends CommonComponent>... excludeComponent) {
        if (null == this.excludeComponents) {
            this.excludeComponents = new HashSet<>(Arrays.asList(excludeComponent));
        } else {
            this.excludeComponents.addAll(Arrays.asList(excludeComponent));
        }
        return this;
    }

    @Override
    public Set<Class<? extends CommonComponent>> getExcludeComponents() {
        return this.excludeComponents;
    }

    @Override
    public SqlCondition getCondition() {
        return sqlCondition;
    }

    @Override
    public boolean cascade() {
        return cascade;
    }

    public void setUpdateFields(Set<String> updateFields) {
        this.updateFields = updateFields;
    }

    @Override
    public Set<String> getUpdateFields() {
        return updateFields;
    }

    @Override
    public void setCondition(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }
}
