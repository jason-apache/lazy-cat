package com.lazy.cat.orm.core.jdbc.analyzer;


import com.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfoWrapper;
import com.lazy.cat.orm.core.jdbc.dto.FlatPojoWrapper;
import com.lazy.cat.orm.core.jdbc.exception.MergeRowException;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import com.lazy.cat.orm.core.jdbc.mapping.On;
import com.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import com.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/13 11:12
 */
public class DefaultRowAggregator implements RowAggregator {

    @Override
    public List<?> mergeRow(TableChainHolder tableChainHolder, ExcludeFieldInfoWrapper exclude, List<FlatPojoWrapper[]> wrapperList) {
        List<Object> mergeResult = new ArrayList<>(wrapperList.size());
        // 根据主键聚合分组
        Map<Object, List<FlatPojoWrapper[]>> groupMap = wrapperList.stream().collect(Collectors.groupingBy(w -> this.getId(tableChainHolder.getTableInfo().getId().getGetter(), w[0].getPojoInstance()), LinkedHashMap::new, Collectors.toList()));
        for (Map.Entry<Object, List<FlatPojoWrapper[]>> entry : groupMap.entrySet()) {
            // 得到一个对象，其他id相同的对象代表一对多映射中 一的一方 的同一条记录，需要set至这个对象中去
            Object mainPojo = entry.getValue().get(0)[0].getPojoInstance();
            if (entry.getValue().get(0).length > 1) {
                try {
                    this.merge(mainPojo, tableChainHolder.getNestedChain(), tableChainHolder.getFlatChain(), exclude, entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MergeRowException("尝试将一对多、一对一、多对一映射合并时发生异常：" + mainPojo.getClass().getName() + "\r\n" + entry.getValue());
                }
            }
            mergeResult.add(mainPojo);
        }
        return mergeResult;
    }

    /**
     * 递归合并属性
     * @param mainPojo 主对象
     * @param nestedChain 嵌套的表链结构
     * @param flatChain 平铺的表链结构
     * @param exclude 排除字段
     * @param flatPojoList 平铺的对象实例集合
     * @throws InvocationTargetException 反射引发的异常
     * @throws IllegalAccessException 反射引发的异常
     * @throws InstantiationException 反射引发的异常
     */
    protected void merge(Object mainPojo, List<TableChain> nestedChain, List<TableChain> flatChain, ExcludeFieldInfoWrapper exclude, List<FlatPojoWrapper[]> flatPojoList)
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (TableChain chain : nestedChain) {
            Method idGetter = chain.getId().getGetter();
            int index = flatChain.indexOf(chain) + 1;
            PojoMapping pojoMapping = chain.getPojoMapping();
            Method setter = pojoMapping.getFieldInfo().getSetter();
            if (pojoMapping instanceof OneToManyMapping) {
                Collection<Object> container = this.buildCollection(((OneToManyMapping) pojoMapping).getContainerType());
                LinkedHashSet<Object> set = new LinkedHashSet<>();
                for (FlatPojoWrapper[] wrappers : flatPojoList) {
                    Object pojo = wrappers[index].getPojoInstance();
                    if (null == this.getId(idGetter, pojo)) {
                        continue;
                    }
                    if (pojo.getClass() == chain.getPojoType() && this.qualified(mainPojo, pojo, chain.getJoinCondition())) {
                        if (chain.hasChain() && !set.contains(pojo)) {
                            this.merge(pojo, chain.getChain(), flatChain, exclude, flatPojoList);
                        }
                        set.add(pojo);
                    }
                }
                container.addAll(set);
                if (!container.isEmpty()) {
                    setter.invoke(mainPojo, container);
                }
            } else {
                Object pojo = flatPojoList.get(0)[index].getPojoInstance();
                if (null == this.getId(idGetter, pojo)) {
                    for (FlatPojoWrapper[] wrappers : flatPojoList) {
                        pojo = wrappers[index].getPojoInstance();
                        if (null != this.getId(idGetter, pojo)) {
                            break;
                        }
                    }
                }
                if (null != this.getId(idGetter, pojo)) {
                    if (chain.hasChain()) {
                        this.merge(pojo, chain.getChain(), flatChain, exclude, flatPojoList);
                    }
                    setter.invoke(mainPojo, pojo);
                }
            }
        }
    }

    private Object getId(Method getter, Object instance) {
        if (null == instance) {
            return null;
        }
        return ReflectionUtils.invokeMethod(getter, instance);
    }

    /**
     * 生成一对多映射中多的一方的容器
     * @param collectionType 容器类型
     * @return 容器实例
     * @throws IllegalAccessException 反射引发的异常
     * @throws InstantiationException 反射引发的异常
     */
    @SuppressWarnings("unchecked")
    private Collection<Object> buildCollection(Class<?> collectionType) throws IllegalAccessException, InstantiationException {
        if (collectionType == List.class) {
            return new ArrayList<>();
        }
        if (collectionType == Set.class) {
            return new HashSet<>();
        }
        return (Collection<Object>) collectionType.newInstance();
    }

    /**
     * 根据关联条件进行对象赋值
     * @param mainPojo 主对象
     * @param target 关联对象
     * @param conditions 关联条件
     * @return 是否满足关联条件
     * @throws InvocationTargetException 反射引发的异常
     * @throws IllegalAccessException 反射引发的异常
     */
    protected boolean qualified(Object mainPojo, Object target, List<On> conditions) throws InvocationTargetException, IllegalAccessException {
        for (On on : conditions) {
            Method foreignKeyGetter = on.getForeignKeyInfo().getGetter();
            Method targetFiledGetter = on.getTargetFiledInfo().getGetter();
            Object result1 = foreignKeyGetter.invoke(mainPojo);
            Object result2 = targetFiledGetter.invoke(target);
            if (!Objects.equals(result1, result2)) {
                return false;
            }
        }
        return true;
    }
}
