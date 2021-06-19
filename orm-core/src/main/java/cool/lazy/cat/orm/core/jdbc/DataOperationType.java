package cool.lazy.cat.orm.core.jdbc;

/**
 * @author: mahao
 * @date: 2021/4/14 16:31
 * 数据操作类型
 */
public enum DataOperationType {
    /**
     * 新增
     */
    INSERT,
    /**
     * 修改
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 逻辑删除
     */
    LOGIC_DELETE
}
