package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.core.jdbc.exception.MergeRowException;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/3/13 11:12
 */
public class DefaultRowAggregator implements RowAggregator {

    @Override
    public List<?> mergeRow(TableInfo tableInfo, FieldAccessor fieldAccessor, List<Object[]> instanceCompound) {
        List<Object> mergeResult = new ArrayList<>(instanceCompound.size());
        if (fieldAccessor.hasOneToManyMapping()) {
            Map<Object, List<Object[]>> groupMap = this.groupById(tableInfo.getId(), instanceCompound);
            //Map<Object, List<InstanceDescriptor[]>> groupMap = instanceCompound.stream().collect(Collectors.groupingBy(w -> this.getId(tableInfo.getId(), w[0]), LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<Object, List<Object[]>> entry : groupMap.entrySet()) {
                // 得到一个对象，其他id相同的对象代表一对多映射中 一的一方 的同一条记录，需要set至这个对象中去
                Object mainPojo = entry.getValue().get(0)[0];
                if (entry.getValue().get(0).length > 1) {
                    try {
                        this.mergeMultipleRows(mainPojo, fieldAccessor.getRootTableNode(), entry.getValue());
                    } catch (Exception e) {
                        throw new MergeRowException("尝试将一对多、一对一、多对一映射合并时发生异常：" + mainPojo.getClass().getName() + "\r\n" + entry.getValue(), e);
                    }
                }
                mergeResult.add(mainPojo);
            }
        } else {
            for (Object[] instances : instanceCompound) {
                Object mainPojo = instances[0];
                if (null == this.getId(tableInfo.getId(), mainPojo)) {
                    continue;
                }
                this.mergeSingleRow(mainPojo, fieldAccessor.getRootTableNode(), instances);
                mergeResult.add(mainPojo);
            }
        }
        return mergeResult;
    }

    protected Map<Object, List<Object[]>> groupById(IdField idField, List<Object[]> instanceCompound) {
        Map<Object, List<Object[]>> groupMap = new LinkedHashMap<>(instanceCompound.size());
        int counter = 0;
        for (Object[] instances : instanceCompound) {
            Object mainPojo = instances[0];
            Object id = this.getId(idField, mainPojo);
            if (null == id) {
                groupMap.put(counter ++, Collections.singletonList(instances));
            } else {
                groupMap.computeIfAbsent(id, o -> new ArrayList<>(100)).add(instances);
            }
        }
        return groupMap;
    }

    /**
     * 递归合并属性
     * @param mainPojo 主对象
     * @param root 遍历表结构的节点
     * @param instanceCompound 平铺的对象实例集合
     */
    protected void mergeMultipleRows(Object mainPojo, TableNode root, List<Object[]> instanceCompound) {
        for (TableNode node : root.getChildren()) {
            PojoMapping pojoMapping = node.getPojoMapping();
            Method setter = pojoMapping.getPojoField().getSetter();
            Class<?> pojoType = node.getPojoType();
            if (pojoMapping instanceof OneToManyMapping) {
                Collection<Object> container = this.buildCollection(((OneToManyMapping) pojoMapping).getContainerType());
                Set<PrimaryObjectKey> set = new HashSet<>(instanceCompound.size());
                for (Object[] objects : instanceCompound) {
                    Object pojo = objects[node.getIndex()];
                    Object id;
                    if (null == pojo || null == (id = this.getId(node.getId(), pojo)) || pojo.getClass() != pojoType) {
                        continue;
                    }
                    PrimaryObjectKey primaryObjectKey = new PrimaryObjectKey(pojoType, id);
                    if (set.contains(primaryObjectKey) || !this.qualified(mainPojo, pojo, node.getPojoMapping().getJoinCondition())) {
                        continue;
                    }
                    if (CollectionUtil.isNotEmpty(node.getChildren())) {
                        this.mergeMultipleRows(pojo, node, instanceCompound);
                    }
                    set.add(primaryObjectKey);
                    container.add(pojo);
                }
                if (!container.isEmpty()) {
                    ReflectUtil.invokeSetter(setter, mainPojo, container);
                }
            } else {
                for (Object[] objects : instanceCompound) {
                    Object pojo = objects[node.getIndex()];
                    if (this.notMatched(node, mainPojo, pojo, pojoType)) {
                        continue;
                    }
                    ReflectUtil.invokeSetter(setter, mainPojo, pojo);
                    if (CollectionUtil.isNotEmpty(node.getChildren())) {
                        this.mergeMultipleRows(pojo, node, instanceCompound);
                    }
                    break;
                }
            }
        }
    }

    protected void mergeSingleRow(Object mainPojo, TableNode root, Object[] instances) {
        for (TableNode node : root.getChildren()) {
            PojoMapping pojoMapping = node.getPojoMapping();
            Method setter = pojoMapping.getPojoField().getSetter();
            Class<?> pojoType = pojoMapping.getJavaType();
            Object pojo = instances[node.getIndex()];
            if (this.notMatched(node, mainPojo, pojo, pojoType)) {
                continue;
            }
            ReflectUtil.invokeSetter(setter, mainPojo, pojo);
            if (CollectionUtil.isNotEmpty(node.getChildren())) {
                this.mergeSingleRow(pojo, node, instances);
            }
        }
    }

    protected boolean notMatched(TableNode node, Object mainPojo, Object pojo, Class<?> pojoType) {
        if (null == pojo || null == this.getId(node.getId(), pojo) || pojo.getClass() != pojoType) {
            return true;
        }
        if (!this.qualified(mainPojo, pojo, node.getPojoMapping().getJoinCondition())) {
            return true;
        }
        return false;
    }

    private Object getId(IdField idField, Object instance) {
        if (null == instance) {
            return null;
        }
        return ReflectUtil.invokeGetter(idField.getGetter(), instance);
    }

    /**
     * 生成一对多映射中多的一方的容器
     * @param collectionType 容器类型
     * @return 容器实例
     */
    @SuppressWarnings("unchecked")
    private Collection<Object> buildCollection(Class<?> collectionType) {
        if (collectionType == List.class) {
            return new ArrayList<>();
        }
        if (collectionType == Set.class) {
            return new HashSet<>();
        }
        return (Collection<Object>) ReflectUtil.newInstance(collectionType);
    }

    /**
     * 根据关联条件进行对象赋值
     * @param mainPojo 主对象
     * @param target 关联对象
     * @param conditions 关联条件
     * @return 是否满足关联条件
     */
    protected boolean qualified(Object mainPojo, Object target, List<On> conditions) {
        for (On on : conditions) {
            Method foreignKeyGetter = on.getForeignKeyInfo().getGetter();
            Method targetFiledGetter = on.getTargetFiledInfo().getGetter();
            Object result1 = ReflectUtil.invokeGetter(foreignKeyGetter, mainPojo);
            Object result2 = ReflectUtil.invokeGetter(targetFiledGetter, target);
            if (!Objects.equals(result1, result2)) {
                return false;
            }
        }
        return true;
    }

    public static final class PrimaryObjectKey {
        private final Class<?> pojoType;
        private final Object id;

        public PrimaryObjectKey(Class<?> pojoType, Object id) {
            this.pojoType = pojoType;
            this.id = id;
        }

        public Class<?> getPojoType() {
            return pojoType;
        }

        public Object getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PrimaryObjectKey that = (PrimaryObjectKey) o;
            return Objects.equals(pojoType, that.pojoType) && Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            int result = pojoType != null ? pojoType.hashCode() : 0;
            result = 31 * result + (id != null ? id.hashCode() : 0);
            return result;
        }
    }
}
