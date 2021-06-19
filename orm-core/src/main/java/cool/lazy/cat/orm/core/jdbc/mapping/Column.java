package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;

/**
 * @author: mahao
 * @date: 2021/3/14 17:36
 * 字段列映射信息
 */
public class Column {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 查询条件，仅供API使用
     */
    private ConditionType queryFilter;
    /**
     * 类型转换器
     */
    private Class<? extends TypeConverter> typeConverter;
    /**
     * 字段是否参与新增
     */
    private boolean insertable;
    /**
     * 字段是否参与修改
     */
    private boolean updatable;
    /**
     * 检查非空字段
     */
    private boolean notNull;
    /**
     * 最小长度，仅CharSequence实现类有效
     */
    private int minLength;
    /**
     * 最大长度，仅CharSequence实现类有效
     */
    private int maxLength;
    /**
     * 不满足最小长度触发的异常信息
     */
    private String minLengthErrorMsg;
    /**
     * 不满足最大长度触发的异常信息
     */
    private String maxLengthErrorMsg;
    /**
     * 验证器信息
     */
    private ValidatorInfo validatorInfo;

    public Column(cool.lazy.cat.orm.core.base.annotation.Column column) {
        this.name = column.name();
        this.queryFilter = column.queryFilter();
        this.typeConverter = column.typeConverter();
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.notNull = column.notNull();
        this.minLength = column.minLength();
        this.maxLength = column.maxLength();
        this.minLengthErrorMsg = column.minLengthErrorMsg();
        this.maxLengthErrorMsg = column.maxLengthErrorMsg();
        this.validatorInfo = new ValidatorInfo(column.validator());
    }

    public ConditionType getQueryFilterType() {
        return queryFilter == null ? ConditionType.NONE : queryFilter;
    }

    public boolean havingTypeConverter() {
        return this.typeConverter != TypeConverter.class;
    }

    public boolean havingValidator() {
        return this.validatorInfo != null && this.validatorInfo.getValidator() != Validator.class;
    }

    public boolean havingSimpleValidate() {
        return this.notNull || this.minLength > 0 || this.maxLength > -1;
    }

    public boolean isInsertable() {
        return this.insertable;
    }

    public boolean isUpdatable() {
        return this.updatable;
    }

    public boolean getNotNull() {
        return this.notNull;
    }

    public String getName() {
        return name;
    }

    public Column setName(String name) {
        this.name = name;
        return this;
    }

    public ConditionType getQueryFilter() {
        return queryFilter;
    }

    public Column setQueryFilter(ConditionType queryFilter) {
        this.queryFilter = queryFilter;
        return this;
    }

    public Class<? extends TypeConverter> getTypeConverter() {
        return typeConverter;
    }

    public Column setTypeConverter(Class<? extends TypeConverter> typeConverter) {
        this.typeConverter = typeConverter;
        return this;
    }

    public Column setInsertable(boolean insertable) {
        this.insertable = insertable;
        return this;
    }

    public Column setUpdatable(boolean updatable) {
        this.updatable = updatable;
        return this;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public Column setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    public int getMinLength() {
        return minLength;
    }

    public Column setMinLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public Column setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public String getMinLengthErrorMsg() {
        return minLengthErrorMsg;
    }

    public Column setMinLengthErrorMsg(String minLengthErrorMsg) {
        this.minLengthErrorMsg = minLengthErrorMsg;
        return this;
    }

    public String getMaxLengthErrorMsg() {
        return maxLengthErrorMsg;
    }

    public Column setMaxLengthErrorMsg(String maxLengthErrorMsg) {
        this.maxLengthErrorMsg = maxLengthErrorMsg;
        return this;
    }

    public ValidatorInfo getValidatorInfo() {
        return validatorInfo;
    }

    public Column setValidatorInfo(ValidatorInfo validatorInfo) {
        this.validatorInfo = validatorInfo;
        return this;
    }
}
