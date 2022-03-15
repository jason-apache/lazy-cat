package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

/**
 * @author: mahao
 * @date: 2021/3/12 13:19
 * 对象关联条件
 */
public class OnImpl implements On {

    private final String foreignField;
    private final String targetFiled;
    private final cool.lazy.cat.orm.annotation.On.AssignmentMethod assignmentMethod;
    private Class<?> foreignPojoType;
    private Class<?> targetPojoType;
    private PojoField foreignKeyInfo;
    private PojoField targetFiledInfo;
    private boolean mappedToSource;

    public OnImpl(cool.lazy.cat.orm.annotation.On on) {
        this.foreignField = on.foreignFiled();
        this.targetFiled = on.targetFiled();
        this.assignmentMethod = on.assignmentMethod();
    }

    @Override
    public Class<?> getForeignPojoType() {
        return foreignPojoType;
    }

    public OnImpl setForeignPojoType(Class<?> foreignPojoType) {
        this.foreignPojoType = foreignPojoType;
        return this;
    }

    @Override
    public Class<?> getTargetPojoType() {
        return targetPojoType;
    }

    public OnImpl setTargetPojoType(Class<?> targetPojoType) {
        this.targetPojoType = targetPojoType;
        return this;
    }

    @Override
    public String getForeignField() {
        return foreignField;
    }

    @Override
    public String getTargetFiled() {
        return targetFiled;
    }

    @Override
    public cool.lazy.cat.orm.annotation.On.AssignmentMethod getAssignmentMethod() {
        return assignmentMethod;
    }

    @Override
    public PojoField getForeignKeyInfo() {
        return foreignKeyInfo;
    }

    @Override
    public void setForeignKeyInfo(PojoField foreignKeyInfo) {
        this.foreignKeyInfo = foreignKeyInfo;
    }

    @Override
    public PojoField getTargetFiledInfo() {
        return targetFiledInfo;
    }

    @Override
    public void setTargetFiledInfo(PojoField targetFiledInfo) {
        this.targetFiledInfo = targetFiledInfo;
    }

    @Override
    public boolean isMappedToSource() {
        return mappedToSource;
    }

    @Override
    public void setMappedToSource(boolean mappedToSource) {
        this.mappedToSource = mappedToSource;
    }
}
