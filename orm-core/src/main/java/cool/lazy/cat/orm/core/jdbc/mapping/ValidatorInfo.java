package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.ParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/10/18 11:45
 * 校验器
 */
public interface ValidatorInfo extends ParameterizationInfo {

    /**
     * @return 校验器类型
     */
    Class<? extends Validator> getValidator();

    /**
     * @return 校验字段内容是否不能为空
     */
    boolean isNotNull();
}
