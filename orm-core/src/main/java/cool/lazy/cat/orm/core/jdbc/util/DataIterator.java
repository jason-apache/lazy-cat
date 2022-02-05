package cool.lazy.cat.orm.core.jdbc.util;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/25 17:05
 * 数据迭代器
 * 将多个或单个数据的遍历方式调整成为一种方式 简化操作
 */
public final class DataIterator {

    private DataIterator() {}

    /**
     * 遍历一个数据源
     * @param data 数据源 可以是数组、集合 也可以是单个对象
     * @param eachPlan 遍历方案
     */
    public static void forEach(Object data, EachPlan eachPlan) {
        if (null == data) {
            return;
        }
        if (data instanceof Collection) {
            for (Object o : ((Collection<?>) data)) {
                if (null == o) {
                    continue;
                }
                eachPlan.action(o);
            }
        } else if (data.getClass().isArray()) {
            for (Object o : ((Object[]) data)) {
                if (null == o) {
                    continue;
                }
                eachPlan.action(o);
            }
        } else {
            eachPlan.action(data);
        }
    }

    /**
     * 遍历方案
     */
    public interface EachPlan {
        void action(Object o);
    }

    /**
     * 从根节点遍历整个表结构 将所有嵌套的节点
     * @param rootTableNode 根对象表结构
     * @param src 根对象
     * @param plan 方案
     */
    public static void forEach(TableNode rootTableNode, Object src, ShuntPlan plan) {
        if (null == src) {
            return;
        }
        if (src instanceof Collection) {
            for (Object o : ((Collection<?>) src)) {
                recursionForEach(rootTableNode, o, plan, 1);
            }
        } else if (src.getClass().isArray()) {
            for (Object o : ((Object[]) src)) {
                recursionForEach(rootTableNode, o, plan, 1);
            }
        } else {
            recursionForEach(rootTableNode, src, plan, 1);
        }
    }

    /**
     * 递归遍历尽所有子节点
     * @param srcTableNode 源表结果
     * @param src 源对象
     * @param plan 方案
     * @param depth 当前深度
     */
    private static void recursionForEach(TableNode srcTableNode, Object src, ShuntPlan plan, int depth) {
        if (CollectionUtil.isEmpty(srcTableNode.getChildren())) {
            return;
        }
        for (TableNode tableNodeChild : srcTableNode.getChildren()) {
            Object newSrc = ReflectUtil.invokeGetter(tableNodeChild.getPojoMapping().getPojoField().getGetter(), src);
            if (null != newSrc) {
                if (!plan.action(src, newSrc, tableNodeChild, srcTableNode, depth)) {
                    continue;
                }
                if (CollectionUtil.isNotEmpty(tableNodeChild.getChildren())) {
                    if (newSrc instanceof Collection) {
                        for (Object o : ((Collection<?>) newSrc)) {
                            recursionForEach(tableNodeChild, o, plan, depth + 1);
                        }
                    } else {
                        recursionForEach(tableNodeChild, newSrc, plan, depth + 1);
                    }
                }
            }
        }
    }

    @FunctionalInterface
    public interface ShuntPlan {
        boolean action(Object parent, Object record, TableNode curNode, TableNode srcTableNode, int depth);
    }
}
