package com.lazy.cat.orm.core.jdbc.mapping;

import com.lazy.cat.orm.core.base.annotation.ManyToOne;
import com.lazy.cat.orm.core.base.annotation.OneToMany;
import com.lazy.cat.orm.core.base.annotation.OneToOne;
import com.lazy.cat.orm.core.base.annotation.Trigger;
import com.lazy.cat.orm.core.base.exception.FieldAlreadyExistsException;
import com.lazy.cat.orm.core.base.exception.UnsupportedTypeException;
import com.lazy.cat.orm.core.base.util.CollectionUtil;
import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/11 12:38
 * 记录pojo类与数据库表信息的映射关系
 */
public class TableInfo {

    /**
     * pojo类型
     */
    private Class<?> pojoType;
    /**
     * 表名称
     */
    private String name;
    /**
     * 表所在的库
     */
    private String schema;
    /**
     * pojo主键
     */
    private IdStrategy id;
    /**
     * pojo字段信息
     */
    private List<TableFieldInfo> fieldInfoList;
    /**
     * pojo一对多映射
     */
    private List<OneToManyMapping> oneToManyMapping;
    /**
     * pojo一对一映射
     */
    private List<OneToOneMapping> oneToOneMapping;
    /**
     * pojo多对一映射
     */
    private List<ManyToOneMapping> manyToOneMapping;
    /**
     * 嵌套的数据库表链式调用关系
     */
    private List<TableChain> nestedChain;
    /**
     * 平铺的数据库表链式调用关系（由嵌套的表链转换得来）
     */
    private List<TableChain> flatChain;
    /**
     * 触发器信息
     */
    private List<TriggerInfo> triggerInfoList;
    /**
     * 逻辑删除字段
     */
    private LogicDeleteField logicDeleteField;

    public void setId(IdStrategy id) {
        if (null != this.id) {
            throw new FieldAlreadyExistsException("重复的id定义：" + this.pojoType.getName() + "#" + id.getGetter().getName());
        }
        this.id = id;
    }

    public void addFiledInfo(TableFieldInfo fieldInfo) {
        if (this.fieldInfoList == null) {
            this.fieldInfoList = new ArrayList<>();
        }
        this.fieldInfoList.add(fieldInfo);
    }

    @SuppressWarnings("unchecked")
    public void addAnnotation(OneToMany oneToMany, Class<?> containerType, TableFieldInfo fieldInfo) {
        if (null == oneToMany) {
            return;
        }
        if (null == oneToManyMapping) {
            oneToManyMapping = new ArrayList<>();
        }
        if (!Collection.class.isAssignableFrom(containerType)) {
            throw new UnsupportedTypeException("暂不支持的集合类型：" + fieldInfo.getPojoType().getName() + "#" + fieldInfo.getGetter().getName() + "#" + containerType.getName());
        }
        Class<?> rawClass = ResolvableType.forMethodReturnType(fieldInfo.getGetter()).getGeneric(0).getRawClass();
        if (null == rawClass) {
            throw new NullPointerException("字段泛型为空：" + fieldInfo.getPojoType().getName() + "#" + fieldInfo.getGetter().getName());
        }
        oneToManyMapping.add(new OneToManyMapping(oneToMany).setJavaType(rawClass).setFieldInfo(fieldInfo)
                .setContainerType((Class<? extends Collection<?>>) containerType).addJoinCondition(oneToMany.condition(), this.pojoType, rawClass));
    }

    public void addAnnotation(OneToOne oneToOne, Class<?> javaType, TableFieldInfo fieldInfo) {
        if (null == oneToOne || null == javaType) {
            return;
        }
        if (null == oneToOneMapping) {
            oneToOneMapping = new ArrayList<>();
        }
        oneToOneMapping.add(new OneToOneMapping(oneToOne).setJavaType(javaType)
                .setFieldInfo(fieldInfo).addJoinCondition(oneToOne.condition(), this.pojoType, javaType));
    }

    public void addAnnotation(ManyToOne manyToOne, Class<?> javaType, TableFieldInfo fieldInfo) {
        if (null == manyToOne || null == javaType) {
            return;
        }
        if (null == manyToOneMapping) {
            manyToOneMapping = new ArrayList<>();
        }
        manyToOneMapping.add(new ManyToOneMapping(manyToOne).setJavaType(javaType)
                .setFieldInfo(fieldInfo).addJoinCondition(manyToOne.condition(), this.pojoType, javaType));
    }

    public void addTrigger(Trigger[] triggers) {
        if (CollectionUtil.isEmpty(triggers)) {
            return;
        }
        if (null == this.triggerInfoList) {
            this.triggerInfoList = new ArrayList<>(triggers.length);
        }
        List<Trigger> triggerList = Arrays.stream(triggers).sorted(Comparator.comparingInt(Trigger::sort)).collect(Collectors.toList());
        for (Trigger trigger : triggerList) {
            if (com.lazy.cat.orm.core.jdbc.component.trigger.Trigger.class == trigger.type()) {
                throw new IllegalArgumentException("不是一个实现类：" + trigger.type().getName());
            }
            this.triggerInfoList.add(new TriggerInfo(trigger));
        }
        this.triggerInfoList.sort(Comparator.comparingInt(TriggerInfo::getSort));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableInfo tableInfo = (TableInfo) o;
        if (!Objects.equals(name, tableInfo.name)) {
            return false;
        }
        return Objects.equals(schema, tableInfo.schema);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        return result;
    }

    public String getFullName() {
        if (null != this.schema) {
            return this.schema + "." + this.name;
        }
        return this.name;
    }

    public boolean isNested() {
        return CollectionUtil.isNotEmpty(this.nestedChain);
    }

    public boolean havingOneToManyMapping() {
        return isNested() && CollectionUtil.isNotEmpty(this.oneToManyMapping);
    }

    public boolean havingTrigger() {
        return CollectionUtil.isNotEmpty(this.triggerInfoList);
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public TableInfo setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public String getName() {
        return name;
    }

    public TableInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public TableInfo setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public IdStrategy getId() {
        return id;
    }

    public List<TableFieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public TableInfo setFieldInfoList(List<TableFieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
        return this;
    }

    public List<OneToManyMapping> getOneToManyMapping() {
        return oneToManyMapping;
    }

    public TableInfo setOneToManyMapping(List<OneToManyMapping> oneToManyMapping) {
        this.oneToManyMapping = oneToManyMapping;
        return this;
    }

    public List<OneToOneMapping> getOneToOneMapping() {
        return oneToOneMapping;
    }

    public TableInfo setOneToOneMapping(List<OneToOneMapping> oneToOneMapping) {
        this.oneToOneMapping = oneToOneMapping;
        return this;
    }

    public List<ManyToOneMapping> getManyToOneMapping() {
        return manyToOneMapping;
    }

    public TableInfo setManyToOneMapping(List<ManyToOneMapping> manyToOneMapping) {
        this.manyToOneMapping = manyToOneMapping;
        return this;
    }

    public List<TableChain> getNestedChain() {
        return nestedChain;
    }

    public TableInfo setNestedChain(List<TableChain> nestedChain) {
        this.nestedChain = nestedChain;
        return this;
    }

    public List<TableChain> getFlatChain() {
        return flatChain;
    }

    public TableInfo setFlatChain(List<TableChain> flatChain) {
        this.flatChain = flatChain;
        return this;
    }

    public List<TriggerInfo> getTriggerInfoList() {
        return triggerInfoList;
    }

    public TableInfo setTriggerInfoList(List<TriggerInfo> triggerInfoList) {
        this.triggerInfoList = triggerInfoList;
        return this;
    }

    public LogicDeleteField getLogicDeleteField() {
        return logicDeleteField;
    }

    public TableInfo setLogicDeleteField(LogicDeleteField logicDeleteField) {
        this.logicDeleteField = logicDeleteField;
        return this;
    }
}
