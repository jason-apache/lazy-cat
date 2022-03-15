package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/3/14 17:36
 * 字段列映射信息
 */
public class ColumnImpl extends AbstractParameterizationInfo implements Column {

    /**
     * 字段名称
     */
    private final String name;
    /**
     * 类型转换器
     */
    private final Class<? extends TypeConverter> typeConverter;
    /**
     * 字段是否参与新增
     */
    private final boolean insertable;
    /**
     * 字段是否参与修改
     */
    private boolean updatable;
    /**
     * 验证器信息
     */
    private final ValidatorInfo validatorInfo;
    /**
     * 排序
     */
    private final int sort;

    public ColumnImpl(cool.lazy.cat.orm.annotation.Column column) {
        if (ReflectUtil.canInstantiate(column.typeConverter())) {
            if (!TypeConverter.class.isAssignableFrom(column.typeConverter())) {
                throw new UnsupportedOperationException("不支持的类型: " + column.typeConverter() + ", 期望的类型: " + TypeConverter.class.getName());
            }
            this.typeConverter = Caster.cast(column.typeConverter());
        } else {
            this.typeConverter = TypeConverter.class;
        }
        this.name = column.name();
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.validatorInfo = new ValidatorInfoImpl(column.validator());
        this.sort = column.sort();
        super.initParameter(column.parameters());
    }

    @Override
    public boolean havingTypeConverter() {
        return this.typeConverter != TypeConverter.class;
    }

    @Override
    public boolean havingValidator() {
        return this.validatorInfo != null && this.validatorInfo.getValidator() != Validator.class;
    }

    @Override
    public boolean isInsertable() {
        return this.insertable;
    }

    @Override
    public boolean isUpdatable() {
        return this.updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<? extends TypeConverter> getTypeConverter() {
        return typeConverter;
    }

    @Override
    public ValidatorInfo getValidatorInfo() {
        return validatorInfo;
    }

    @Override
    public int getSort() {
        return sort;
    }
}
