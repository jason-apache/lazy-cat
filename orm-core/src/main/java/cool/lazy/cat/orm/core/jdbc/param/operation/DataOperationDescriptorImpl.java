package cool.lazy.cat.orm.core.jdbc.param.operation;

import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/11/1 16:27
 */
public class DataOperationDescriptorImpl implements DataOperationDescriptor {

    private Map<Class<?>, DataOperationItem> insertData = Collections.emptyMap();
    private Map<Class<?>, DataOperationItem> updateData = Collections.emptyMap();
    private Map<Class<?>, DataOperationItem> logicDeleteData = Collections.emptyMap();
    private Map<Class<?>, DataOperationItem> deleteData = Collections.emptyMap();
    private Set<TableNode> affectedTable;

    @Override
    public void registryInsert(DataOperationItem item) {
        if (insertData == Collections.EMPTY_MAP) {
            insertData = new LinkedHashMap<>();
        }
        insertData.put(item.getPojoType(), item);
    }

    @Override
    public void registryUpdate(DataOperationItem item) {
        if (updateData == Collections.EMPTY_MAP) {
            updateData = new LinkedHashMap<>();
        }
        updateData.put(item.getPojoType(), item);
    }

    @Override
    public void registryDelete(DataOperationItem item) {
        if (deleteData == Collections.EMPTY_MAP) {
            deleteData = new LinkedHashMap<>();
        }
        deleteData.put(item.getPojoType(), item);
    }

    @Override
    public void registryLogicDelete(DataOperationItem item) {
        if (logicDeleteData == Collections.EMPTY_MAP) {
            logicDeleteData = new LinkedHashMap<>();
        }
        logicDeleteData.put(item.getPojoType(), item);
    }

    @Override
    public Collection<DataOperationItem> getInsertData() {
        return insertData.values();
    }

    @Override
    public Collection<DataOperationItem> getUpdateData() {
        return updateData.values();
    }

    @Override
    public Collection<DataOperationItem> getLogicDeleteData() {
        return logicDeleteData.values();
    }

    @Override
    public Collection<DataOperationItem> getDeleteData() {
        return deleteData.values();
    }

    @Override
    public Set<TableNode> getAffectedTable() {
        return affectedTable;
    }

    @Override
    public void setAffectedTable(Set<TableNode> affectedTable) {
        this.affectedTable = affectedTable;
    }
}
