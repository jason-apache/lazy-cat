package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction.TransactionTemplateManager;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-02-03 13:50
 * 提供基本事务支持
 */
public interface ServiceTransactionSupport {

    default TransactionTemplateManager adaptTransactionTemplateManager(Class<? extends SqlType> action, Class<?> pojoType) {
        return this.adaptTransactionTemplateManager(action, pojoType, Collections.emptyMap());
    }

    /**
     * 适配一个数据源持有对象 返回这个对象的事务管理器
     * @param action sql类型
     * @param pojoType 操作pojo类型
     * @param params 参数
     * @return 事务管理器
     */
    TransactionTemplateManager adaptTransactionTemplateManager(Class<? extends SqlType> action, Class<?> pojoType, Map<String, Object> params);
}
