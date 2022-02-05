package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.constant.JoinMode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 12:59
 */
public class ManyToOneMappingImpl implements ManyToOneMapping, PojoMapping {

    private final Class<?> javaType;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private List<On> joinCondition;
    private final int cascadeLevel;
    private final String[] cascadeScope;
    private final String[] ignoreFields;
    private final PojoField pojoField;
    private final boolean insertable;
    private final boolean updatable;
    private final boolean deletable;
    private final int sort;
    private boolean havingMappedToSource;
    private final JoinMode joinMode;

    public ManyToOneMappingImpl(Class<?> javaType, PojoField pojoField, ManyToOne manyToOne) {
        this.javaType = javaType;
        this.pojoField = pojoField;
        this.insertable = manyToOne.insertable();
        this.updatable = manyToOne.updatable();
        this.deletable = manyToOne.deletable();
        this.cascadeLevel = manyToOne.cascadeLevel();
        this.cascadeScope = manyToOne.cascadeScope();
        this.ignoreFields = manyToOne.ignoreFields();
        this.sort = manyToOne.sort();
        this.joinMode = manyToOne.mode();
    }

    public ManyToOneMappingImpl addJoinCondition(cool.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType, Class<?> targetPojoType) {
        if (joinCondition == null) {
            joinCondition = new ArrayList<>(onArr.length);
        }
        for (cool.lazy.cat.orm.core.base.annotation.On on : onArr) {
            joinCondition.add(new OnImpl(on).setForeignPojoType(foreignPojoType).setTargetPojoType(targetPojoType));
        }
        return this;
    }

    @Override
    public Class<?> getJavaType() {
        return javaType;
    }

    @Override
    public List<On> getJoinCondition() {
        return joinCondition;
    }

    @Override
    public int getCascadeLevel() {
        return cascadeLevel;
    }

    @Override
    public String[] getCascadeScope() {
        return cascadeScope;
    }

    @Override
    public String[] getIgnoreFields() {
        return ignoreFields;
    }

    @Override
    public PojoField getPojoField() {
        return pojoField;
    }

    @Override
    public boolean isInsertable() {
        return insertable;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    @Override
    public boolean isDeletable() {
        return deletable;
    }

    @Override
    public int sort() {
        return sort;
    }

    @Override
    public boolean havingMappedToSource() {
        return havingMappedToSource;
    }

    @Override
    public void setHavingMappedToSource(boolean havingMappedToSource) {
        this.havingMappedToSource = havingMappedToSource;
    }

    @Override
    public JoinMode getJoinMode() {
        return joinMode;
    }

    @Override
    public String toString() {
        return getPojoField().getJavaFieldName();
    }
}
