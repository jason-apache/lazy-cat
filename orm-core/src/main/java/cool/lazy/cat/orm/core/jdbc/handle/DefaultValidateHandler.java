package cool.lazy.cat.orm.core.jdbc.handle;

import cool.lazy.cat.orm.core.base.util.InvokeHelper;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.provider.ValidatorProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/31 18:58
 */
public class DefaultValidateHandler implements ValidateHandler {

    @Autowired
    protected ValidatorProvider validatorProvider;

    @Override
    public void handle(TableFieldInfo fieldInfo, Object data) {
        Validator validator = validatorProvider.provider(fieldInfo.getColumn().getValidatorInfo().getValidator());
        Method getter = fieldInfo.getGetter();
        if (data instanceof Collection) {
            Collection<?> dataRef = (Collection<?>) data;
            for (Object o : dataRef) {
                Object val = InvokeHelper.invokeGetter(getter, o);
                validator.validate(fieldInfo.getColumn(), val);
            }
        } else {
            Object val = InvokeHelper.invokeGetter(getter, data);
            validator.validate(fieldInfo.getColumn(), val);
        }
    }
}
