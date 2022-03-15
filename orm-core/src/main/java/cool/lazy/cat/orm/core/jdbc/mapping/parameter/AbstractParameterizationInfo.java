package cool.lazy.cat.orm.core.jdbc.mapping.parameter;

import cool.lazy.cat.orm.annotation.Parameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2022-01-28 21:50
 */
public abstract class AbstractParameterizationInfo implements ParameterizationInfo {

    protected Map<String, String> parameterMapping;

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
    public String getParameterValue(String name) {
        return parameterMapping.get(name);
    }

    @Override
    public Set<String> keys() {
        return parameterMapping == null ? Collections.emptySet() : parameterMapping.keySet();
    }
}
