package com.lazy.cat.orm.core.base;

/**
 * @author: mahao
 * @date: 2021/3/4 12:45
 */
public interface FullAutomaticMapping {

    /**
     * 获取持久对象泛型的class
     * @return 持久化对象class
     */
    Class<?> getPojoType();
}
