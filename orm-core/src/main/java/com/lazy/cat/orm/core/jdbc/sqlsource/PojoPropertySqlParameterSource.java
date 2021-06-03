package com.lazy.cat.orm.core.jdbc.sqlsource;

import com.lazy.cat.orm.core.base.util.InvokeHelper;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/29 20:38
 */
public class PojoPropertySqlParameterSource extends AbstractSqlParameterSource {

    protected final Object pojo;
    protected final Map<String, TableFieldInfo> fieldInfoMap;
    protected final Map<TableFieldInfo, Object> convertedData;

    public PojoPropertySqlParameterSource(Object pojo, Map<String, TableFieldInfo> fieldInfoMap, Map<TableFieldInfo, Object> convertedData) {
        this.pojo = pojo;
        this.fieldInfoMap = fieldInfoMap;
        this.convertedData = convertedData;
    }

    @Override
    public boolean hasValue(String paramName) {
        return fieldInfoMap.containsKey(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        TableFieldInfo fieldInfo = fieldInfoMap.get(paramName);
        if (null != this.convertedData && this.convertedData.containsKey(fieldInfo)) {
            return convertedData.get(fieldInfo);
        }
        return InvokeHelper.invokeGetter(fieldInfo.getGetter(), pojo);
    }

    @Override
    public int getSqlType(String paramName) {
        Class<?> javaType = fieldInfoMap.get(paramName).getJavaType();
        // todo Oracle 11g无法处理boolean|char
        if (javaType == boolean.class || javaType == Boolean.class || javaType == char.class || javaType == Character.class) {
            javaType = String.class;
        }
        return StatementCreatorUtils.javaTypeToSqlParameterType(javaType);
    }

    @Override
    public String[] getParameterNames() {
        return fieldInfoMap.values().stream().map(TableFieldInfo::getJavaFieldName).toArray(String[]::new);
    }
}
