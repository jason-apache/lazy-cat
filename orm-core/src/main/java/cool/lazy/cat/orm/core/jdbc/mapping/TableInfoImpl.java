package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.base.exception.FieldAlreadyExistsException;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapper;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/11 12:38
 * 记录pojo类与数据库表信息的映射关系
 */
public class TableInfoImpl extends AbstractParameterizationInfo implements TableInfo {

    /**
     * pojo类型
     */
    private final Class<?> pojoType;
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
    private IdField id;
    /**
     * pojo字段信息
     */
    private Map<String, PojoField> fieldInfoMap;
    private List<PojoField> havingTypeConverterFields;
    private List<PojoField> havingValidatorFields;
    private List<PojoMapping> mappings = Collections.emptyList();
    /**
     * 触发器信息
     */
    private List<TriggerInfo> triggerInfoList;
    /**
     * 逻辑删除字段
     */
    private LogicDeleteField logicDeleteField;
    private PojoFieldMapper fieldMapper;
    /**
     * 是否存在一对多映射
     */
    private Boolean havingOneToManyMapping;
    /**
     * 是否存在映射结果赋值至源对象的情况
     */
    private Boolean havingMappedToSource;
    private List<PojoMapping> havingMappedToSourceMappings;

    public TableInfoImpl(Class<?> pojoType, Table table) {
        this.pojoType = pojoType;
        super.initParameter(table.parameter());
    }

    public void setId(IdField id) {
        if (null != this.id) {
            throw new FieldAlreadyExistsException("重复的id定义：" + this.pojoType.getName() + "#" + id.getGetter().getName());
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableInfoImpl tableInfoImpl = (TableInfoImpl) o;
        if (!Objects.equals(name, tableInfoImpl.name)) {
            return false;
        }
        return Objects.equals(schema, tableInfoImpl.schema);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        return result;
    }

    @Override
    public boolean havingOneToManyMapping() {
        if (null == havingOneToManyMapping) {
            havingOneToManyMapping = false;
            for (PojoMapping mapping : mappings) {
                if (mapping instanceof OneToManyMapping) {
                    havingOneToManyMapping = true;
                    break;
                }
            }
        }
        return havingOneToManyMapping;
    }

    @Override
    public boolean havingMappedToSource() {
        if (null == havingMappedToSource) {
            havingMappedToSource = false;
            for (PojoMapping mapping : mappings) {
                if (mapping.havingMappedToSource()) {
                    havingMappedToSource = true;
                    break;
                }
            }
        }
        return havingMappedToSource;
    }

    @Override
    public boolean havingTrigger() {
        return CollectionUtil.isNotEmpty(this.triggerInfoList);
    }

    @Override
    public void setTriggerInfoList(List<TriggerInfo> triggerInfoList) {
        this.triggerInfoList = triggerInfoList;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public IdField getId() {
        return id;
    }

    @Override
    public Map<String, PojoField> getFieldInfoMap() {
        return fieldInfoMap;
    }

    public void setFieldInfoMap(Map<String, PojoField> fieldInfoMap) {
        this.fieldInfoMap = fieldInfoMap;
    }

    @Override
    public boolean havingTypeConverter() {
        return !this.getHavingTypeConverterFields().isEmpty();
    }

    @Override
    public boolean havingValidator() {
        return !this.getHavingValidatorFields().isEmpty();
    }

    @Override
    public List<PojoField> getHavingTypeConverterFields() {
        if (havingTypeConverterFields == null) {
            havingTypeConverterFields = fieldInfoMap.values().stream().filter(PojoField::havingTypeConverter).collect(Collectors.toList());
            if (havingTypeConverterFields.isEmpty()) {
                havingTypeConverterFields = Collections.emptyList();
            }
        }
        return havingTypeConverterFields;
    }

    @Override
    public List<PojoField> getHavingValidatorFields() {
        if (havingValidatorFields == null) {
            havingValidatorFields = fieldInfoMap.values().stream().filter(PojoField::havingValidator).collect(Collectors.toList());
            if (havingValidatorFields.isEmpty()) {
                havingValidatorFields = Collections.emptyList();
            }
        }
        return havingValidatorFields;
    }

    @Override
    public List<PojoMapping> getHavingMappedToSourceMappings() {
        if (havingMappedToSourceMappings == null) {
            if (this.havingMappedToSource()) {
                havingMappedToSourceMappings = new ArrayList<>();
                for (PojoMapping mapping : mappings) {
                    if (mapping.havingMappedToSource()) {
                        havingMappedToSourceMappings.add(mapping);
                    }
                }
            } else {
                havingMappedToSourceMappings = Collections.emptyList();
            }
        }
        return havingMappedToSourceMappings;
    }

    @Override
    public List<PojoMapping> getMappings() {
        return mappings;
    }
    
    @Override
    public void setMappings(List<PojoMapping> mappings) {
        this.mappings = mappings;
    }

    @Override
    public List<TriggerInfo> getTriggerInfoList() {
        return triggerInfoList;
    }

    @Override
    public LogicDeleteField getLogicDeleteField() {
        return logicDeleteField;
    }

    public void setLogicDeleteField(LogicDeleteField logicDeleteField) {
        this.logicDeleteField = logicDeleteField;
    }

    @Override
    public PojoFieldMapper getFieldMapper() {
        return fieldMapper;
    }

    @Override
    public void setFieldMapper(PojoFieldMapper fieldMapper) {
        this.fieldMapper = fieldMapper;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
