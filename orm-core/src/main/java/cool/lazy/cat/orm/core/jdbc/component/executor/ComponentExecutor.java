package cool.lazy.cat.orm.core.jdbc.component.executor;

import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;

/**
 * @author: mahao
 * @date: 2021/9/20 14:25
 * 通用插件执行器
 */
public interface ComponentExecutor {

    boolean matched(DataHolderParam param, DataOperationDescriptor operationDescriptor);

    /**
     * 增|改 时触发插件执行
     * @param param 增删改参数
     * @param operationDescriptor 增删改数据
     */
    void execute(DataHolderParam param, DataOperationDescriptor operationDescriptor);
}
