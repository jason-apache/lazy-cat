package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.annotation.OneToOne;
import cool.lazy.cat.orm.core.base.constant.JoinMode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 17:36
 */
public class OneToOneMappingImpl implements OneToOneMapping, PojoMapping {

    private final Class<?> javaType;
    private final int cascadeLevel;
    private final String[] cascadeScope;
    private final String[] ignoreFields;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private List<On> joinCondition;
    private final PojoField pojoField;
    private final boolean insertable;
    private final boolean updatable;
    private final boolean deletable;
    private final int sort;
    private boolean havingMappedToSource;
    private final JoinMode joinMode;

    public OneToOneMappingImpl(Class<?> javaType, PojoField pojoField, OneToOne oneToOne) {
        this.javaType = javaType;
        this.pojoField = pojoField;
        this.insertable = oneToOne.insertable();
        this.updatable = oneToOne.updatable();
        this.deletable = oneToOne.deletable();
        this.cascadeLevel = oneToOne.cascadeLevel();
        this.cascadeScope = oneToOne.cascadeScope();
        this.ignoreFields = oneToOne.ignoreFields();
        this.sort = oneToOne.sort();
        this.joinMode = oneToOne.mode();
    }

    public OneToOneMappingImpl addJoinCondition(cool.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType, Class<?> targetPojoType) {
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
    public List<On> getJoinCondition() {
        return joinCondition;
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
