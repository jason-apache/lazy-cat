package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.base.annotation.OneToMany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 13:26
 */
public class OneToManyMapping implements PojoMapping {

    private Class<?> javaType;
    private int cascadeLevel;
    /**
     * 一对多容器类型
     */
    private Class<? extends Collection<?>> containerType;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private List<On> joinCondition;
    private TableFieldInfo fieldInfo;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;

    protected OneToManyMapping(OneToMany oneToMany) {
        this.insertable = oneToMany.insertable();
        this.updatable = oneToMany.updatable();
        this.deletable = oneToMany.deletable();
        this.cascadeLevel = oneToMany.cascadeLevel();
    }

    public OneToManyMapping addJoinCondition(com.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType, Class<?> targetPojoType) {
        if (joinCondition == null) {
            joinCondition = new ArrayList<>();
        }
        for (com.lazy.cat.orm.core.base.annotation.On on : onArr) {
            joinCondition.add(new On(on).setForeignPojoType(foreignPojoType).setTargetPojoType(targetPojoType));
        }
        return this;
    }

    @Override
    public Class<?> getJavaType() {
        return javaType;
    }

    public OneToManyMapping setJavaType(Class<?> javaType) {
        this.javaType = javaType;
        return this;
    }

    @Override
    public int getCascadeLevel() {
        return cascadeLevel;
    }

    public OneToManyMapping setCascadeLevel(int cascadeLevel) {
        this.cascadeLevel = cascadeLevel;
        return this;
    }

    public Class<? extends Collection<?>> getContainerType() {
        return containerType;
    }

    public OneToManyMapping setContainerType(Class<? extends Collection<?>> containerType) {
        this.containerType = containerType;
        return this;
    }

    @Override
    public List<On> getJoinCondition() {
        return joinCondition;
    }

    public OneToManyMapping setJoinCondition(List<On> joinCondition) {
        this.joinCondition = joinCondition;
        return this;
    }

    @Override
    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public OneToManyMapping setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }

    @Override
    public boolean isInsertable() {
        return insertable;
    }

    public OneToManyMapping setInsertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    public OneToManyMapping setUpdatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    @Override
    public boolean isDeletable() {
        return deletable;
    }

    public OneToManyMapping setDeletable(boolean deletable) {
        this.deletable = deletable;
        return this;
    }
}
