package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.Ignorer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/8/2 15:28
 * 对象字段访问器
 * 对象关联层级可能嵌套很多层 通过cascadeLevel或cascadeScope已经可以观测到关联的具体层级
 * 不同对象的表别名、字段别名、嵌套顺序、关联关系都不同 因此不能复用
 * 所以需要为每一个pojo都分配一个字段访问器
 */
public interface FieldAccessor extends Iterable<FieldDescriptor> {

    /**
     * 初始化方法 加载对象所有字段及映射对象
     */
    void init();

    /**
     * 设置忽略访问的字段
     * @param ignorer 忽略字段
     */
    void setIgnore(Ignorer ignorer);

    /**
     * @return 根对象表结构
     */
    TableNode getRootTableNode();

    /**
     * @return 对象表结构映射 完整路径:表结构
     */
    Map<String, TableNode> getTableNodeMapping();

    /**
     * 根据字段完整名称获取字段
     * @param name 完整名称
     * @return 字段
     */
    FieldDescriptor get(String name);

    /**
     * @return 全部字段
     */
    List<FieldDescriptor> getFieldDescriptors();

    /**
     * @return 是否包含一对多映射
     */
    boolean hasOneToManyMapping();

    /**
     * @return 表结构是否嵌套
     */
    boolean nested();

    @Override
    default Iterator<FieldDescriptor> iterator() {
        return this.getFieldDescriptors().iterator();
    }

    /**
     * 获得表链顺序建议 提供在增删改时参考
     * @param reversed 是否翻转顺序
     * @return 顺序建议
     */
    Class<?>[] getTableLinkOrderRecommend(boolean reversed);
}
