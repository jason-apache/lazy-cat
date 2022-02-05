package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapper;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.ParameterizationInfo;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/10/18 11:31
 * 数据库表信息
 */
public interface TableInfo extends ParameterizationInfo {

    /**
     * @return 是否包含一对多映射
     */
    boolean havingOneToManyMapping();

    /**
     * @return 是否存在映射对象字段赋值至源对象的情况
     */
    boolean havingMappedToSource();

    /**
     * @return 是否存在触发器
     */
    boolean havingTrigger();

    void setTriggerInfoList(List<TriggerInfo> triggerInfoList);

    /**
     * @return 对应pojo类型
     */
    Class<?> getPojoType();

    /**
     * @return 表名
     */
    String getName();

    /**
     * @return schema
     */
    String getSchema();

    /**
     * @return id字段
     */
    IdField getId();

    /**
     * @return 所有列映射 字段名:字段
     */
    Map<String, PojoField> getFieldInfoMap();

    /**
     * @return 是否存在类型转换器
     */
    boolean havingTypeConverter();

    /**
     * @return 是否存在校验器
     */
    boolean havingValidator();

    /**
     * @return 存在类型转换器的字段
     */
    List<PojoField> getHavingTypeConverterFields();

    /**
     * @return 存在校验器的字段
     */
    List<PojoField> getHavingValidatorFields();

    List<PojoMapping> getHavingMappedToSourceMappings();

    /**
     * @return 对象映射
     */
    List<PojoMapping> getMappings();

    void setMappings(List<PojoMapping> mappings);

    /**
     * @return 触发器集合
     */
    List<TriggerInfo> getTriggerInfoList();

    /**
     * @return 逻辑删除字段
     */
    LogicDeleteField getLogicDeleteField();

    /**
     * @return 字段别名、对象结构映射
     */
    PojoFieldMapper getFieldMapper();

    void setFieldMapper(PojoFieldMapper fieldMapper);
}
