package cool.lazy.cat.orm.core.base.repository.impl;


import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.base.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.component.executor.ComponentExecutor;
import cool.lazy.cat.orm.base.component.id.IdGenerator;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.DeleteParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptorImpl;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItem;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItemImpl;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.sql.DataInfo;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.PageSqlInterceptor;
import cool.lazy.cat.orm.core.jdbc.sql.source.LogicDeleteSqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.type.Delete;
import cool.lazy.cat.orm.core.jdbc.sql.type.DeleteByInfer;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.sql.type.LogicDelete;
import cool.lazy.cat.orm.core.jdbc.sql.type.Save;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.jdbc.util.DataIterator;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/5 10:04
 */
public abstract class AbstractBaseRepository implements BaseRepository {

    protected final SqlExecutor sqlExecutor;
    protected final PojoTableManager pojoTableManager;
    protected final IdGeneratorProvider idGeneratorProvider;
    protected final List<ComponentExecutor> componentExecutorList;
    protected final JdbcOperationHolderAdapter jdbcOperationHolderAdapter;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected AbstractBaseRepository(SqlExecutor sqlExecutor, PojoTableManager pojoTableManager, IdGeneratorProvider idGeneratorProvider, List<ComponentExecutor> componentExecutorList, JdbcOperationHolderAdapter jdbcOperationHolderAdapter) {
        this.sqlExecutor = sqlExecutor;
        this.pojoTableManager = pojoTableManager;
        this.idGeneratorProvider = idGeneratorProvider;
        this.componentExecutorList = componentExecutorList;
        this.jdbcOperationHolderAdapter = jdbcOperationHolderAdapter;
    }

    @Override
    public <T> List<T> query(SearchParam<T> searchParam) {
        return sqlExecutor.query(searchParam).getMetaData();
    }

    @Override
    public void update(UpdateParam updateParam) {
        DataOperationDescriptor operationDescriptor;
        if (SqlSource.class.isAssignableFrom(CollectionUtil.getTypeFromObj(updateParam.getData()))) {
            // 使用sqlSource更新
            updateParam.setPojoType(this.getRealPojoType(updateParam.getData()));
            operationDescriptor = new DataOperationDescriptorImpl();
            DataOperationItem updateItem = new DataOperationItemImpl(updateParam.getPojoType());
            updateItem.setCondition(updateItem.getCondition());
            DataIterator.forEach(updateParam.getData(), (updateItem::add));
            operationDescriptor.registryUpdate(updateItem);
            // 触发组件
            this.executeComponent(updateParam, operationDescriptor);
        } else {
            // 使用pojo更新
            operationDescriptor = this.buildOperationDescriptor(Save.class, updateParam.getData(), updateParam.cascade());
            if (null == updateParam.getCondition()) {
                if (CollectionUtil.isNotEmpty(operationDescriptor.getUpdateData())) {
                    for (DataOperationItem item : operationDescriptor.getUpdateData()) {
                        item.setCondition(this.buildBasicIdCondition(item.getPojoType()));
                    }
                }
            }
            this.beforeUpdate(updateParam.getData(), updateParam, operationDescriptor, updateParam.cascade());
        }
        sqlExecutor.save(Save.class, updateParam, operationDescriptor);
    }

    protected Class<?> getRealPojoType(Object data) {
        if (data instanceof Collection) {
            return ((SqlSource)((Collection<?>) data).iterator().next()).getPojoType();
        } else if (data.getClass().isArray()) {
            return ((SqlSource[]) data)[0].getPojoType();
        } else {
            return ((SqlSource) data).getPojoType();
        }
    }

    @Override
    public void save(Object data, boolean cascade) {
        if (CollectionUtil.sizeOf(data) < 1) {
            return;
        }
        DataOperationDescriptor dataOperationDescriptor = this.buildOperationDescriptor(Save.class, data, cascade);
        DataHolderParamImpl param = new DataHolderParamImpl(CollectionUtil.getTypeFromObj(data));
        this.beforeUpdate(data, param, dataOperationDescriptor, cascade);
        if (CollectionUtil.isNotEmpty(dataOperationDescriptor.getUpdateData())) {
            for (DataOperationItem item : dataOperationDescriptor.getUpdateData()) {
                item.setCondition(this.buildBasicIdCondition(item.getPojoType()));
            }
        }
        sqlExecutor.save(Save.class, param, dataOperationDescriptor);
    }

    @Override
    public void delete(DeleteParam deleteParam) {
        DataOperationDescriptor operationDescriptor;
        if (null != deleteParam.getData()) {
            // 根据pojo删除
            operationDescriptor = this.buildOperationDescriptor(Delete.class, deleteParam.getData(), deleteParam.cascade());
            if (CollectionUtil.isNotEmpty(operationDescriptor.getDeleteData())) {
                for (DataOperationItem item : operationDescriptor.getDeleteData()) {
                    if (CollectionUtil.isEmpty(item.values())) {
                        continue;
                    }
                    SqlCondition condition = this.buildBasicIdCondition(item.getPojoType());
                    if (null != deleteParam.getCondition()) {
                        condition.and(deleteParam.getCondition());
                    }
                    item.setCondition(condition);
                }
            }
        } else {
            // 根据条件删除
            operationDescriptor = new DataOperationDescriptorImpl();
            DataOperationItem deleteItem = new DataOperationItemImpl(deleteParam.getPojoType());
            deleteItem.setCondition(deleteItem.getCondition());
            operationDescriptor.registryDelete(deleteItem);
            if (null == deleteParam.getCondition()) {
                logger.warn("无条件的删除: {}", deleteParam.getPojoType());
            }
        }
        sqlExecutor.save(Delete.class, deleteParam, operationDescriptor);
    }

    @Override
    public void logicDelete(Object data, boolean cascade) {
        if (CollectionUtil.sizeOf(data) < 1) {
            return;
        }
        DataOperationDescriptor dataOperationDescriptor = this.buildOperationDescriptor(LogicDelete.class, data, cascade);
        if (CollectionUtil.isEmpty(dataOperationDescriptor.getLogicDeleteData())) {
            return;
        }
        this.reRegisterLogicData(dataOperationDescriptor);
        DataHolderParamImpl dataHolderParam = new DataHolderParamImpl(CollectionUtil.getTypeFromObj(data));
        this.executeComponent(dataHolderParam, dataOperationDescriptor);
        sqlExecutor.save(LogicDelete.class, dataHolderParam, dataOperationDescriptor);
    }

    @Override
    public void deleteByInfer(Object data, boolean cascade) {
        if (CollectionUtil.sizeOf(data) < 1) {
            return;
        }
        DataOperationDescriptor dataOperationDescriptor = this.buildOperationDescriptor(DeleteByInfer.class, data, cascade);
        if (CollectionUtil.isNotEmpty(dataOperationDescriptor.getLogicDeleteData())) {
            this.reRegisterLogicData(dataOperationDescriptor);
        }
        if (CollectionUtil.isNotEmpty(dataOperationDescriptor.getDeleteData())) {
            for (DataOperationItem item : dataOperationDescriptor.getDeleteData()) {
                item.setCondition(this.buildBasicIdCondition(item.getPojoType()));
            }
        }
        DataHolderParamImpl dataHolderParam = new DataHolderParamImpl(CollectionUtil.getTypeFromObj(data));
        this.executeComponent(dataHolderParam, dataOperationDescriptor);
        sqlExecutor.save(DeleteByInfer.class, dataHolderParam, dataOperationDescriptor);
    }

    private void reRegisterLogicData(DataOperationDescriptor dataOperationDescriptor) {
        List<DataOperationItem> itemList = new ArrayList<>();
        for (DataOperationItem item : dataOperationDescriptor.getLogicDeleteData()) {
            // 将待修改的实体转换为逻辑删除资源
            List<LogicDeleteSqlSource> logicDeleteSqlSources = this.convertToLogicDeleteSource(item.values());
            DataOperationItem logicDeleteItem = new DataOperationItemImpl(item.getPojoType());
            logicDeleteSqlSources.forEach(logicDeleteItem::add);
            // 设置条件
            logicDeleteItem.setCondition(this.buildBasicIdCondition(logicDeleteItem.getPojoType()));
            itemList.add(logicDeleteItem);
        }
        dataOperationDescriptor.getLogicDeleteData().clear();
        itemList.forEach(dataOperationDescriptor::registryLogicDelete);
    }

    /**
     * 将需要逻辑删除的实体转换为逻辑删除资源
     * @param objects 实体集合
     * @return 更新字段集合
     */
    protected List<LogicDeleteSqlSource> convertToLogicDeleteSource(Collection<Object> objects) {
        Class<?> pojoType = CollectionUtil.getTypeFromObj(objects);
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        IdField idField = tableInfo.getId();
        LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
        return objects.stream().map(o -> new LogicDeleteSqlSource(pojoType, idField, ReflectUtil.invokeGetter(idField.getGetter(), o), logicDeleteField)).collect(Collectors.toList());
    }

    @Override
    public void deleteByIdsAndInfer(Class<?> pojoType, Object ids) {
        if (this.hasLogicDeleteField(pojoType)) {
            this.logicDeleteByIds(pojoType, ids);
        } else {
            this.deleteByIds(pojoType, ids);
        }
    }

    @Override
    public <T> PageResult<T> queryPage(SearchParam<T> searchParam) {
        DataInfo<T> dataInfo = sqlExecutor.query(searchParam);
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageContent(dataInfo.getMetaData());
        if (searchParam.needPaging()) {
            Object totalCount = dataInfo.getMetaInfo().get(PageSqlInterceptor.TOTAL_COUNT_KEY);
            pageResult.setTotalCount(totalCount == null ? 0L : Long.parseLong(totalCount.toString()));
        }
        return pageResult;
    }

    /**
     * 从pojo实例中获取id的值
     * @param instance pojo实例
     * @return id值
     */
    protected Object getPojoId(Object instance) {
        return ReflectUtil.invokeGetter(this.pojoTableManager.getByPojoType(instance.getClass()).getTableInfo().getId().getGetter(), instance);
    }

    protected boolean isNewRecord(Object instance) {
        return null == this.getPojoId(instance);
    }

    protected boolean hasLogicDeleteField(Class<?> pojoType) {
        return this.pojoTableManager.getByPojoType(pojoType).getTableInfo().getLogicDeleteField() != null;
    }

    protected SqlCondition buildBasicIdCondition(Class<?> pojoType) {
        IdField id = pojoTableManager.getByPojoType(pojoType).getTableInfo().getId();
        return Condition.eq(id.getJavaFieldName(), id::getJavaFieldName);
    }

    /**
     * 构建增删改数据
     * @param type sql类型
     * @param data 操作数据
     * @param cascade 是否级联操作
     * @return 数据描述对象
     */
    protected DataOperationDescriptor buildOperationDescriptor(Class<? extends SqlType> type, Object data, boolean cascade) {
        if (null == data) {
            return new DataOperationDescriptorImpl();
        }
        final DataOperationDescriptor dataOperationDescriptor = new DataOperationDescriptorImpl();
        final boolean isSave = Save.class.isAssignableFrom(type);
        final boolean isDelete = Delete.class.isAssignableFrom(type);
        final boolean isLogicDelete = LogicDelete.class.isAssignableFrom(type);
        Map<Class<?>, DataOperationItem> insertData = new LinkedHashMap<>();
        Map<Class<?>, DataOperationItem> updateData = new LinkedHashMap<>();
        Map<Class<?>, DataOperationItem> deleteData = new LinkedHashMap<>();
        Map<Class<?>, DataOperationItem> logicDeleteData = new LinkedHashMap<>();
        DataIterator.forEach(data, (o -> {
            if (isSave) {
                if (this.isNewRecord(o)) {
                    insertData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                } else {
                    updateData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                }
            } else if (isLogicDelete || isDelete) {
                if (isLogicDelete && this.hasLogicDeleteField(o.getClass())) {
                    logicDeleteData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                } else if (isDelete) {
                    deleteData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                }
            }
        }));
        if (!cascade) {
            insertData.values().forEach(dataOperationDescriptor::registryInsert);
            updateData.values().forEach(dataOperationDescriptor::registryUpdate);
            logicDeleteData.values().forEach(dataOperationDescriptor::registryLogicDelete);
            deleteData.values().forEach(dataOperationDescriptor::registryDelete);
            return dataOperationDescriptor;
        }
        dataOperationDescriptor.setAffectedTable(new HashSet<>());
        Class<?> pojoType = CollectionUtil.getTypeFromObj(data);
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        FieldAccessor fieldAccessor = tableInfo.getFieldMapper().getFieldAccessor();
        fieldAccessor.init();
        // 处理级联对象
        DataIterator.forEach(fieldAccessor.getRootTableNode(), data, ((parent, record, curNode, srcTableNode, depth) -> {
            // 如果没有级联对象的增|删|改权限 则跳过循环
            final boolean[] interrupt = {false};
            DataIterator.forEach(record, (o -> {
                if (isSave) {
                    if (this.isNewRecord(o)) {
                        if (!curNode.getPojoMapping().isInsertable()) {
                            interrupt[0] = true;
                            return;
                        }
                        insertData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                    } else {
                        if (!curNode.getPojoMapping().isUpdatable()) {
                            interrupt[0] = true;
                            return;
                        }
                        updateData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                    }
                    dataOperationDescriptor.getAffectedTable().add(curNode);
                } else if (isLogicDelete || isDelete) {
                    if (!curNode.getPojoMapping().isDeletable()) {
                        interrupt[0] = true;
                        return;
                    }
                    if (isLogicDelete && this.hasLogicDeleteField(o.getClass())) {
                        logicDeleteData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                        dataOperationDescriptor.getAffectedTable().add(curNode);
                    } else if (isDelete) {
                        deleteData.computeIfAbsent(o.getClass(), i -> new DataOperationItemImpl(o.getClass())).add(o);
                        dataOperationDescriptor.getAffectedTable().add(curNode);
                    }
                }
            }));
            return !interrupt[0];
        }));
        this.sortData(tableInfo, false, insertData, updateData, logicDeleteData);
        // 删除操作需要反转顺序
        this.sortData(tableInfo, true, deleteData);
        insertData.values().forEach(dataOperationDescriptor::registryInsert);
        updateData.values().forEach(dataOperationDescriptor::registryUpdate);
        logicDeleteData.values().forEach(dataOperationDescriptor::registryLogicDelete);
        deleteData.values().forEach(dataOperationDescriptor::registryDelete);
        return dataOperationDescriptor;
    }

    @SafeVarargs
    protected final void sortData(TableInfo tableInfo, boolean reverse, Map<Class<?>, DataOperationItem>... itemMaps) {
        if (!tableInfo.havingMappedToSource()) {
            return;
        }
        // 根据持久化顺序建议重新排序
        Class<?>[] tableLinkOrderRecommend = tableInfo.getFieldMapper().getFieldAccessor().getTableLinkOrderRecommend(reverse);
        if (CollectionUtil.isEmpty(tableLinkOrderRecommend)) {
            return;
        }
        for (Map<Class<?>, DataOperationItem> itemMap : itemMaps) {
            if (itemMap.keySet().size() <= 1) {
                continue;
            }
            Map<Class<?>, DataOperationItem> itemMapStub = new LinkedHashMap<>(itemMap);
            itemMap.clear();
            for (Class<?> type : tableLinkOrderRecommend) {
                DataOperationItem item = itemMapStub.get(type);
                if (null != item) {
                    itemMap.put(type, item);
                }
            }
        }
    }

    /**
     * 更新级联对象之前的操作
     * @param src 源对象
     * @param dataHolderParam 数据集
     * @param operationDescriptor 数据操作描述
     * @param cascade 是否级联
     */
    protected void beforeUpdate(Object src, DataHolderParam dataHolderParam, DataOperationDescriptor operationDescriptor, boolean cascade) {
        if (CollectionUtil.isNotEmpty(operationDescriptor.getInsertData())) {
            this.injectId(dataHolderParam.getPojoType(), operationDescriptor.getInsertData());
        }
        if (cascade) {
            FieldAccessor fieldAccessor = pojoTableManager.getByPojoType(CollectionUtil.getTypeFromObj(src)).getTableInfo().getFieldMapper().getFieldAccessor();
            fieldAccessor.init();
            Set<TableNode> affectedTable = operationDescriptor.getAffectedTable();
            // 重新赋值 对象与关联对象 关联条件的字段
            DataIterator.forEach(fieldAccessor.getRootTableNode(), src, ((parent, record, curNode, srcTableNode, depth) -> {
                DataIterator.forEach(record, (o -> {
                    for (On on : curNode.getPojoMapping().getJoinCondition()) {
                        PojoField foreignKeyInfo = on.getForeignKeyInfo();
                        PojoField targetFiledInfo = on.getTargetFiledInfo();
                        if (on.isMappedToSource()) {
                            ReflectUtil.invokeSetter(foreignKeyInfo.getSetter(), parent, ReflectUtil.invokeGetter(targetFiledInfo.getGetter(), o));
                        } else {
                            if (null != affectedTable && affectedTable.contains(curNode)) {
                                ReflectUtil.invokeSetter(targetFiledInfo.getSetter(), o, ReflectUtil.invokeGetter(foreignKeyInfo.getGetter(), parent));
                            }
                        }
                    }
                }));
                return true;
            }));
        }
        this.executeComponent(dataHolderParam, operationDescriptor);
    }

    /**
     * 注入id属性
     * @param pojoType 对象类型
     * @param insertData 新增数据
     */
    protected void injectId(Class<?> pojoType, Collection<DataOperationItem> insertData) {
        JdbcOperationHolder temp = JdbcOperationSupport.get();
        JdbcOperationHolder jdbcOperationHolder = jdbcOperationHolderAdapter.adapt(Insert.class, pojoType);
        // 生成id也许会操作数据库 此处先绑定线程操作jdbc对象
        JdbcOperationSupport.set(jdbcOperationHolder);
        try {
            for (DataOperationItem item : insertData) {
                IdField id = pojoTableManager.getByPojoType(item.getPojoType()).getTableInfo().getId();
                List<Object> nullIdValueList = item.values().stream().filter(o -> ReflectUtil.invokeGetter(id.getGetter(), o) == null).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(nullIdValueList)) {
                    IdGenerator idGenerator = idGeneratorProvider.provider(id.getIdGenerator());
                    List<Object> ids = idGenerator.generator(nullIdValueList);
                    int index = 0;
                    for (Object o : nullIdValueList) {
                        ReflectUtil.invokeSetter(id.getSetter(), o, ids.get(index ++));
                    }
                }
            }
            jdbcOperationHolder.release();
        } finally {
            // 清除
            JdbcOperationSupport.clear();
            if (null != temp) {
                JdbcOperationSupport.set(temp);
            }
        }
    }

    protected void executeComponent(DataHolderParam dataHolderParam, DataOperationDescriptor operationDescriptor) {
        for (ComponentExecutor executor : componentExecutorList) {
            if (executor.matched(dataHolderParam, operationDescriptor)) {
                executor.execute(dataHolderParam, operationDescriptor);
            }
        }
    }

    protected static class DataHolderParamImpl implements DataHolderParam {

        private final Class<?> pojoType;

        public DataHolderParamImpl(Class<?> pojoType) {
            this.pojoType = pojoType;
        }

        @Override
        public Object getData() {
            return null;
        }

        @Override
        public boolean cascade() {
            return false;
        }

        @Override
        public Class<?> getPojoType() {
            return pojoType;
        }

        @Override
        public SqlCondition getCondition() {
            return null;
        }

        @Override
        public Set<Class<? extends CommonComponent>> getExcludeComponents() {
            return null;
        }
    }

}
