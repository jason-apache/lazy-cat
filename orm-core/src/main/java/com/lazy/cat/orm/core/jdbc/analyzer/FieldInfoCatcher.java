package com.lazy.cat.orm.core.jdbc.analyzer;

import com.lazy.cat.orm.core.base.util.Ignorer;
import com.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfoWrapper;
import com.lazy.cat.orm.core.jdbc.dto.TableFieldInfoWrapper;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/16 13:53
 * 属性捕获器
 */
public interface FieldInfoCatcher {

    /**
     * 根据属性名称，从pojo类中尝试获取该属性
     * @param pojoType pojo类型
     * @param fieldName 属性名
     * @param strictModel 严格模式，如果pojo中不存在该属性，则抛出UnKnowFiledException
     * @return 属性详细信息
     */
    TableFieldInfo getByName(Class<?> pojoType, String fieldName, boolean strictModel);

    /**
     * 根据属性名称，从pojo类中尝试获取该属性
     * @param tableInfo pojo表信息
     * @param fieldName 属性名
     * @param strictModel 严格模式，如果pojo中不存在该属性，则抛出UnKnowFiledException
     * @return 属性详细信息
     */
    TableFieldInfo getByName(TableInfo tableInfo, String fieldName, boolean strictModel);

    /**
     * 获取一个嵌套的属性，根据属性名称，尝试获取该属性
     * 请确保该属性在cascadeLevel作用范围之中
     * 它可以是一个属性，也可以是一个映射的对象
     *      eg： parent.name、parent.parent
     * @param nestedChainList pojo表信息
     * @param fieldName 属性名
     * @param strictModel 严格模式，如果映射对象中不存在该属性，则抛出UnKnowFiledException
     * @return 嵌套属性
     */
    TableFieldInfoWrapper getNestedFiledByName(List<TableChain> nestedChainList, String fieldName, boolean strictModel);

    /**
     * 解析忽略字段，返回被忽略的字段或者对象
     * @param pojoType pojo类型
     * @param nestedChainList 嵌套的表链结构
     * @param ignorer 忽略字段
     * @return 被忽略的字段或者对象
     */
    ExcludeFieldInfoWrapper filterExclude(Class<?> pojoType, List<TableChain> nestedChainList, Ignorer ignorer);

    /**
     * 检查字段是否全部属于pojo类，而不是一个嵌套的属性
     * @param pojoType pojo类型
     * @param fieldsName 字段集
     * @return 字段是否全部属于pojo类
     */
    boolean pojoFieldOnly(Class<?> pojoType, String[] fieldsName);
}
