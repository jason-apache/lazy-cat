package cool.lazy.cat.orm.core.jdbc.param.operation;

import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;

import java.util.Collection;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/11/1 15:16
 * 数据操作描述对象
 */
public interface DataOperationDescriptor {

    /**
     * 注册新增操作对象
     * @param item 操作对象
     */
    void registryInsert(DataOperationItem item);

    /**
     * 注册更新操作对象
     * @param item 操作对象
     */
    void registryUpdate(DataOperationItem item);

    /**
     * 注册逻辑删除操作对象
     * @param item 操作对象
     */
    void registryLogicDelete(DataOperationItem item);

    /**
     * 注册物理删除操作对象
     * @param item 操作对象
     */
    void registryDelete(DataOperationItem item);

    /**
     * @return 新增操作数据
     */
    Collection<DataOperationItem> getInsertData();

    /**
     * @return 更新操作数据
     */
    Collection<DataOperationItem> getUpdateData();

    /**
     * @return 逻辑删除操作数据
     */
    Collection<DataOperationItem> getLogicDeleteData();

    /**
     * @return 物理删除操作数据
     */
    Collection<DataOperationItem> getDeleteData();

    /**
     * @return 受影响的表信息
     */
    Set<TableNode> getAffectedTable();

    void setAffectedTable(Set<TableNode> affectedTable);
}
