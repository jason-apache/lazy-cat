package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction.TransactionTemplateManager;
import cool.lazy.cat.orm.core.jdbc.sql.type.Delete;
import cool.lazy.cat.orm.core.jdbc.sql.type.DeleteByInfer;
import cool.lazy.cat.orm.core.jdbc.sql.type.LogicDelete;
import cool.lazy.cat.orm.core.jdbc.sql.type.Save;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-02-03 13:52
 */
public abstract class AbstractServiceSupport implements ServiceTransactionSupport {

    @Autowired
    private JdbcOperationHolderAdapter jdbcOperationHolderAdapter;

    @Override
    public TransactionTemplateManager adaptTransactionTemplateManager(Class<? extends SqlType> action, Class<?> pojoType, Map<String, Object> params) {
        return jdbcOperationHolderAdapter.adapt(action, pojoType, params).getTransactionTemplateManager();
    }

    protected JdbcOperationHolderAdapter getJdbcOperationHolderAdapter() {
        return jdbcOperationHolderAdapter;
    }

    /**
     * 适配 '无' sql类型
     */
    protected TransactionTemplateManager getTransactionTemplate(Class<?> pojoType) {
        return this.adaptTransactionTemplateManager(SqlType.class, pojoType);
    }

    protected TransactionTemplateManager getSelectTransactionTemplate(Class<?> pojoType) {
        return this.getSelectTransactionTemplate(pojoType, Collections.emptyMap());
    }

    /**
     * 适配Select操作
     */
    protected TransactionTemplateManager getSelectTransactionTemplate(Class<?> pojoType, Map<String, Object> params) {
        return this.adaptTransactionTemplateManager(Select.class, pojoType, params);
    }

    protected TransactionTemplateManager getSaveTransactionTemplate(Class<?> pojoType) {
        return this.getSaveTransactionTemplate(pojoType, Collections.emptyMap());
    }

    /**
     * 适配Save操作
     */
    protected TransactionTemplateManager getSaveTransactionTemplate(Class<?> pojoType, Map<String, Object> params) {
        return this.adaptTransactionTemplateManager(Save.class, pojoType, params);
    }

    protected TransactionTemplateManager getDeleteTransactionTemplate(Class<?> pojoType) {
        return this.getDeleteTransactionTemplate(pojoType, Collections.emptyMap());
    }

    /**
     * 适配Delete操作
     */
    protected TransactionTemplateManager getDeleteTransactionTemplate(Class<?> pojoType, Map<String, Object> params) {
        return this.adaptTransactionTemplateManager(Delete.class, pojoType, params);
    }

    protected TransactionTemplateManager getDeleteByInferTransactionTemplate(Class<?> pojoType) {
        return this.getDeleteByInferTransactionTemplate(pojoType, Collections.emptyMap());
    }

    /**
     * 适配DeleteByInfer操作
     */
    protected TransactionTemplateManager getDeleteByInferTransactionTemplate(Class<?> pojoType, Map<String, Object> params) {
        return this.adaptTransactionTemplateManager(DeleteByInfer.class, pojoType, params);
    }

    protected TransactionTemplateManager getLogicDeleteTransactionTemplate(Class<?> pojoType) {
        return this.getLogicDeleteTransactionTemplate(pojoType, Collections.emptyMap());
    }

    /**
     * 适配LogicDelete操作
     */
    protected TransactionTemplateManager getLogicDeleteTransactionTemplate(Class<?> pojoType, Map<String, Object> params) {
        return this.adaptTransactionTemplateManager(LogicDelete.class, pojoType, params);
    }
}
