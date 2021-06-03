package com.lazy.cat.orm.core.jdbc.sqlsource;


import com.lazy.cat.orm.core.base.util.InvokeHelper;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import org.springframework.jdbc.core.StatementCreatorUtils;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/4/12 21:06
 */
public class UpdateConditionSqlParameterSource extends PojoPropertySqlParameterSource {

    private final Map<String, Object> conditionMap;

    public UpdateConditionSqlParameterSource(Object pojo, Map<String, TableFieldInfo> fieldInfoMap, Map<TableFieldInfo, Object> convertedData, Map<String, Object> conditionMap) {
        super(pojo, fieldInfoMap, convertedData);
        this.conditionMap = conditionMap;
    }

    @Override
    public boolean hasValue(String paramName) {
        if (null != this.conditionMap) {
            return fieldInfoMap.containsKey(paramName) || conditionMap.containsKey(paramName);
        }
        return fieldInfoMap.containsKey(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        TableFieldInfo fieldInfo = fieldInfoMap.get(paramName);
        if (null != this.convertedData && this.convertedData.containsKey(fieldInfo)) {
            return convertedData.get(fieldInfo);
        }
        if (null != this.conditionMap && conditionMap.containsKey(paramName)) {
            return conditionMap.get(paramName);
        }
        return InvokeHelper.invokeGetter(fieldInfo.getGetter(), pojo);
    }

    @Override
    public int getSqlType(String paramName) {
        if (null != this.conditionMap && conditionMap.containsKey(paramName)) {
            Object val = conditionMap.get(paramName);
            if (null == val) {
                return 0;
            }
            return StatementCreatorUtils.javaTypeToSqlParameterType(val.getClass());
        }
        return StatementCreatorUtils.javaTypeToSqlParameterType(fieldInfoMap.get(paramName).getJavaType());
    }

    @Override
    public String[] getParameterNames() {
        return fieldInfoMap.values().stream().map(TableFieldInfo::getJavaFieldName).toArray(String[]::new);
    }
}
