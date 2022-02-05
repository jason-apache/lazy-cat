package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/8/23 10:22
 * 表结构
 */
public interface TableNode {

    /**
     * @return id字段
     */
    IdField getId();

    /**
     * @return id描述
     */
    FieldDescriptor getIdDescriptor();

    FieldDescriptor setIdDescriptor(FieldDescriptor idDescriptor);

    /**
     * @return 路径描述
     */
    PathDescriptor getPath();

    /**
     * @return schema
     */
    String getSchema();

    /**
     * @return 表名
     */
    String tableName();

    /**
     * @return 表别名
     */
    String tableAliasName();

    void setTableAliasName(String tableAliasName);

    /**
     * @return 对应pojo类型
     */
    Class<?> getPojoType();

    /**
     * @return 所属父级表
     */
    TableNode getBelongPojoTable();

    void setChildren(List<TableNode> children);

    /**
     * @return 子表(关联表)
     */
    List<TableNode> getChildren();

    void setFieldMapping(Map<String, FieldDescriptor> fieldMapping);

    /**
     * @return 字段映射 字段名:字段
     */
    Map<String, FieldDescriptor> getFieldMapping();

    /**
     * @return 所属映射结构
     */
    PojoMapping getPojoMapping();

    /**
     * @return 表位置索引
     */
    int getIndex();

    void setIndex(int index);
}
