package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/3/31 16:41
 * 验证器信息
 */
public class ValidatorInfoImpl extends AbstractParameterizationInfo implements ValidatorInfo {

    /**
     * 验证器类型
     */
    private final Class<? extends Validator> validator;
    /**
     * 非空字段
     */
    private final boolean notNull;

    public ValidatorInfoImpl(cool.lazy.cat.orm.core.base.annotation.Validator validator) {
        this.validator = validator.type();
        this.notNull = validator.notNull();
        super.initParameter(validator.parameter());
    }

    @Override
    public Class<? extends Validator> getValidator() {
        return validator;
    }

    @Override
    public boolean isNotNull() {
        return notNull;
    }
}
