package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.base.annotation.ManyToOne;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 12:59
 */
public class ManyToOneMapping implements PojoMapping {

    private Class<?> javaType;
    private List<On> joinCondition;
    private int cascadeLevel;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private TableFieldInfo fieldInfo;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;

    protected ManyToOneMapping(ManyToOne manyToOne) {
        this.insertable = manyToOne.insertable();
        this.updatable = manyToOne.updatable();
        this.deletable = manyToOne.deletable();
        this.cascadeLevel = manyToOne.cascadeLevel();
    }

    public ManyToOneMapping addJoinCondition(com.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType
            , Class<?> targetPojoType) {
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

    public ManyToOneMapping setJavaType(Class<?> javaType) {
        this.javaType = javaType;
        return this;
    }

    @Override
    public List<On> getJoinCondition() {
        return joinCondition;
    }

    public ManyToOneMapping setJoinCondition(List<On> joinCondition) {
        this.joinCondition = joinCondition;
        return this;
    }

    @Override
    public int getCascadeLevel() {
        return cascadeLevel;
    }

    public ManyToOneMapping setCascadeLevel(int cascadeLevel) {
        this.cascadeLevel = cascadeLevel;
        return this;
    }

    @Override
    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public ManyToOneMapping setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }

    @Override
    public boolean isInsertable() {
        return insertable;
    }

    public ManyToOneMapping setInsertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    public ManyToOneMapping setUpdatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    @Override
    public boolean isDeletable() {
        return deletable;
    }

    public ManyToOneMapping setDeletable(boolean deletable) {
        this.deletable = deletable;
        return this;
    }
}
