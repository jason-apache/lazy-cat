package cool.lazy.cat.orm.core.jdbc.mapping.parameter;

import cool.lazy.cat.orm.annotation.Parameter;

import java.util.Set;

/**
 * @author: mahao
 * @date: 2022-01-28 21:49
 */
public interface ParameterizationInfo {

    /**
     * 初始化参数信息
     * @param parameters 参数
     */
    void initParameter(Parameter[] parameters);

    /**
     * @param name 参数名称
     * @return 参数值
     */
    String getParameterValue(String name);

    Set<String> keys();
}
