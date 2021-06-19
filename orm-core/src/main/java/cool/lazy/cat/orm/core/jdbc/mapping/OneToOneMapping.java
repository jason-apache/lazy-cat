package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.annotation.OneToOne;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 17:36
 */
public class OneToOneMapping implements PojoMapping {

    private Class<?> javaType;
    private int cascadeLevel;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private List<On> joinCondition;
    private TableFieldInfo fieldInfo;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;
    

    protected OneToOneMapping(OneToOne oneToOne) {
        this.insertable = oneToOne.insertable();
        this.updatable = oneToOne.updatable();
        this.deletable = oneToOne.deletable();
        this.cascadeLevel = oneToOne.cascadeLevel();
    }

    public OneToOneMapping addJoinCondition(cool.lazy.cat.orm.core.base.annotation.On[] onArr, Class<?> foreignPojoType, Class<?> targetPojoType) {
        if (joinCondition == null) {
            joinCondition = new ArrayList<>();
        }
        for (cool.lazy.cat.orm.core.base.annotation.On on : onArr) {
            joinCondition.add(new On(on).setForeignPojoType(foreignPojoType).setTargetPojoType(targetPojoType));
        }
        return this;
    }

    @Override
    public Class<?> getJavaType() {
        return javaType;
    }

    public OneToOneMapping setJavaType(Class<?> javaType) {
        this.javaType = javaType;
        return this;
    }

    @Override
    public int getCascadeLevel() {
        return cascadeLevel;
    }

    public OneToOneMapping setCascadeLevel(int cascadeLevel) {
        this.cascadeLevel = cascadeLevel;
        return this;
    }

    @Override
    public List<On> getJoinCondition() {
        return joinCondition;
    }

    public OneToOneMapping setJoinCondition(List<On> joinCondition) {
        this.joinCondition = joinCondition;
        return this;
    }

    @Override
    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public OneToOneMapping setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }

    @Override
    public boolean isInsertable() {
        return insertable;
    }

    public OneToOneMapping setInsertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    public OneToOneMapping setUpdatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    @Override
    public boolean isDeletable() {
        return deletable;
    }

    public OneToOneMapping setDeletable(boolean deletable) {
        this.deletable = deletable;
        return this;
    }
}
