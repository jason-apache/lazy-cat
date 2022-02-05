package cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author: mahao
 * @date: 2021/10/20 14:43
 * 事务管理工具
 */
public class StandardTransactionTemplateManager implements TransactionTemplateManager {

    protected final TransactionTemplate readOnly;
    protected final TransactionTemplate supports;
    protected final TransactionTemplate notSupported;
    protected final TransactionTemplate required;
    protected final TransactionTemplate requiresNew;
    protected final TransactionTemplate nested;
    protected final TransactionTemplate never;
    protected final TransactionTemplate mandatory;

    public StandardTransactionTemplateManager(PlatformTransactionManager transactionManager) {
        readOnly = new TransactionTemplate(transactionManager);
        readOnly.setReadOnly(true);
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        supports = new TransactionTemplate(transactionManager);
        supports.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        notSupported = new TransactionTemplate(transactionManager);
        notSupported.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        required = new TransactionTemplate(transactionManager);
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiresNew = new TransactionTemplate(transactionManager);
        requiresNew.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        nested = new TransactionTemplate(transactionManager);
        nested.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        never = new TransactionTemplate(transactionManager);
        never.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
        mandatory = new TransactionTemplate(transactionManager);
        mandatory.setPropagationBehavior(TransactionDefinition.PROPAGATION_MANDATORY);
    }

    @Override
    public <T> T readOnly(TransactionCallback<T> action) {
        return readOnly.execute(action);
    }

    @Override
    public <T> T required(TransactionCallback<T> action) {
        return required.execute(action);
    }

    @Override
    public <T> T requiresNew(TransactionCallback<T> action) {
        return requiresNew.execute(action);
    }

    @Override
    public <T> T supports(TransactionCallback<T> action) {
        return supports.execute(action);
    }

    @Override
    public <T> T notSupported(TransactionCallback<T> action) {
        return notSupported.execute(action);
    }

    @Override
    public <T> T never(TransactionCallback<T> action) {
        return never.execute(action);
    }

    @Override
    public <T> T mandatory(TransactionCallback<T> action) {
        return mandatory.execute(action);
    }

    @Override
    public <T> T nested(TransactionCallback<T> action) {
        return nested.execute(action);
    }
}
