package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.constant.JoinMode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 13:26
 */
public class OneToManyMappingImpl implements OneToManyMapping, PojoMapping {

    private final Class<?> javaType;
    private final int cascadeLevel;
    private final String[] cascadeScope;
    private final String[] ignoreFields;
    /**
     * 一对多容器类型
     */
    private final Class<? extends Collection<?>> containerType;
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

    public OneToManyMappingImpl(Class<?> javaType, Class<? extends Collection<?>> containerType, PojoField pojoField, OneToMany oneToMany) {
        this.javaType = javaType;
        this.containerType = containerType;
        this.pojoField = pojoField;
        this.insertable = oneToMany.insertable();
        this.updatable = oneToMany.updatable();
        this.deletable = oneToMany.deletable();
        this.cascadeLevel = oneToMany.cascadeLevel();
        this.cascadeScope = oneToMany.cascadeScope();
        this.ignoreFields = oneToMany.ignoreFields();
        this.sort = oneToMany.sort();
        this.joinMode = oneToMany.mode();
    }

    public OneToManyMappingImpl addJoinCondition(cool.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType, Class<?> targetPojoType) {
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
    public Class<? extends Collection<?>> getContainerType() {
        return containerType;
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
