package cool.lazy.cat.orm.core.jdbc.component.executor;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TriggerInfo;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationItem;
import cool.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.sql.type.LogicDelete;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.jdbc.sql.type.Update;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/20 14:43
 */
public class TriggerExecutor implements ComponentExecutor {

    protected final PojoTableManager pojoTableManager;
    protected final TriggerProvider triggerProvider;

    public TriggerExecutor(PojoTableManager pojoTableManager, TriggerProvider triggerProvider) {
        this.pojoTableManager = pojoTableManager;
        this.triggerProvider = triggerProvider;
    }

    @Override
    public boolean matched(DataHolderParam param, DataOperationDescriptor operationDescriptor) {
        return CollectionUtil.isEmpty(param.getExcludeComponents()) || !param.getExcludeComponents().contains(Trigger.class);
    }

    @Override
    public void execute(DataHolderParam param, DataOperationDescriptor operationDescriptor) {
        if (CollectionUtil.isNotEmpty(operationDescriptor.getInsertData())) {
            this.trigger(Insert.class, operationDescriptor.getInsertData());
        }
        if (CollectionUtil.isNotEmpty(operationDescriptor.getUpdateData())) {
            this.trigger(Update.class, operationDescriptor.getUpdateData());
        }
        if (CollectionUtil.isNotEmpty(operationDescriptor.getLogicDeleteData())) {
            this.trigger(LogicDelete.class, operationDescriptor.getLogicDeleteData());
        }
    }

    public void trigger(Class<? extends SqlType> sqlType, Collection<DataOperationItem> items) {
        for (DataOperationItem item : items) {
            if (CollectionUtil.isEmpty(item.values())) {
                continue;
            }
            TableInfo tableInfo = pojoTableManager.getByPojoType(item.getPojoType()).getTableInfo();
            List<TriggerInfo> triggerInfoList = tableInfo.getTriggerInfoList();
            if (CollectionUtil.isNotEmpty(triggerInfoList)) {
                for (Object o : item) {
                    for (TriggerInfo triggerInfo : triggerInfoList) {
                        Trigger trigger = triggerProvider.provider(triggerInfo.getTrigger());
                        trigger.execute(sqlType, o);
                    }
                }
            }
        }
    }

}
