package cool.lazy.cat.orm.core.jdbc.sql.executor;

import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.DataInfo;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/7/13 17:12
 * sql执行器
 */
public interface SqlExecutor {

    /**
     * 查询
     * @param searchParam 查询参数
     * @param <T> 泛型类型
     * @return 包装了元数据的数据结构
     */
    <T> DataInfo<T> query(SearchParam<T> searchParam);

    /**
     * 根据操作数据描述 执行 增|删|改|逻辑删除操作
     * @param sqlType sql操作类型
     * @param dataHolderParam 数据操作参数
     * @param operationDescriptor 数据操作描述对象
     */
    void save(Class<? extends SqlType> sqlType, DataHolderParam dataHolderParam, DataOperationDescriptor operationDescriptor);
}
