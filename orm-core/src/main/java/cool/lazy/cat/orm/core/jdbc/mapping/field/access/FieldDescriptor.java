package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

/**
 * @author: mahao
 * @date: 2021/8/18 16:05
 * 字段描述
 */
public interface FieldDescriptor extends PojoField {

    /**
     * @return 真实字段引用
     */
    PojoField getRealField();

    /**
     * @return 所属表结构
     */
    TableNode getTableNode();

    /**
     * @return 完整路径
     */
    PathDescriptor getPath();

    /**
     * @return 所属对象位置索引
     */
    int getMoldIndex();

    /**
     * 设置对应列的位置索引
     * @param columnIndex 所属列索引
     */
    void setColumnIndex(int columnIndex);

    /**
     * @return 所属列索引
     */
    int getColumnIndex();

    /**
     * @return 字段是否被忽略访问
     */
    boolean isIgnored();

    /**
     * @return 是否是一个强制字段(无法被忽略)
     */
    boolean forced();

    void setIgnored(boolean ignored);

    /**
     * @return 别名
     */
    String getAliasName();

    void setAliasName(String aliasName);
}
