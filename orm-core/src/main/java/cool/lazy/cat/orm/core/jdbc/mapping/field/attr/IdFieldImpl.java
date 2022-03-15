package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;


import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.base.component.IdGenerator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/3/30 19:45
 * pojo主键
 */
public class IdFieldImpl extends TableFieldInfo implements IdField {

    /**
     * id生成器类型
     */
    private final Class<? extends IdGenerator> idGenerator;

    protected Map<String, String> parameterMapping;

    public IdFieldImpl(Method getter, Method setter, Class<? extends IdGenerator> idGenerator) {
        super(getter, setter);
        this.idGenerator = idGenerator;
    }

    /*@Override
    public void initColumn(Column column) {
        if (null == column) {
            return;
        }
        if (null == this.column) {
            ColumnImpl c = new ColumnImpl(column);
            // id字段不参与更新
            c.setUpdatable(false);
            this.column = c;
        }
    }*/

    @Override
    public void initParameter(Parameter[] parameters) {
        if (null == parameters || parameters.length <= 0) {
            return;
        }
        if (parameters.length == 1) {
            this.parameterMapping = Collections.singletonMap(parameters[0].name(), parameters[0].value());
        } else {
            this.parameterMapping = new HashMap<>(parameters.length);
            for (Parameter parameter : parameters) {
                parameterMapping.put(parameter.name(), parameter.value());
            }
        }
    }

    @Override
    public Set<String> keys() {
        return parameterMapping == null ? Collections.emptySet() : parameterMapping.keySet();
    }

    @Override
    public String getParameterValue(String name) {
        return parameterMapping.get(name);
    }

    @Override
    public Class<? extends IdGenerator> getIdGenerator() {
        return idGenerator;
    }

}
