package com.lazy.cat.orm.core.jdbc.provider.impl;

import com.lazy.cat.orm.core.base.exception.InitFailedException;
import com.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import com.lazy.cat.orm.core.jdbc.provider.TypeConverterProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/31 19:43
 */
public class DefaultTypeConverterProvider implements TypeConverterProvider {

    protected Map<Class<? extends TypeConverter>, TypeConverter> typeConverterMap = new HashMap<>();

    @Autowired(required = false)
    private void initTypeConverterMap(List<TypeConverter> typeConverterList) {
        this.typeConverterMap = typeConverterList.stream().collect(Collectors.toMap(TypeConverter::getClass, Function.identity()));
    }


    @Override
    public TypeConverter provider(Class<? extends TypeConverter> converterType) {
        TypeConverter converter = typeConverterMap.get(converterType);
        if (null == converter) {
            try {
                converter = converterType.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new InitFailedException("初始化typeConverter失败");
            }
            typeConverterMap.put(converterType, converter);
        }
        return converter;
    }
}
