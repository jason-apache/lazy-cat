package com.lazy.cat.orm.core.jdbc.provider;


import com.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;

/**
 * @author: mahao
 * @date: 2021/3/31 19:43
 * 类型转换器提供者
 */
public interface TypeConverterProvider {

    /**
     * 根据类型提供一个类型转换器
     * @param converterType 类型转换器类型
     * @return 类型转换器
     */
    TypeConverter provider(Class<? extends TypeConverter> converterType);
}
