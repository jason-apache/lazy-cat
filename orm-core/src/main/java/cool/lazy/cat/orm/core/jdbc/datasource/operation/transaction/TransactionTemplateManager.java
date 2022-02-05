package cool.lazy.cat.orm.core.jdbc.datasource.operation.transaction;

import org.springframework.transaction.support.TransactionCallback;

/**
 * @author: mahao
 * @date: 2022-02-03 14:36
 */
public interface TransactionTemplateManager {

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED
     */
    <T> T readOnly(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRED
     */
    <T> T required(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_REQUIRES_NEW
     */
    <T> T requiresNew(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_SUPPORTS
     */
    <T> T supports(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_NOT_SUPPORTED
     */
    <T> T notSupported(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_NEVER
     */
    <T> T never(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_MANDATORY
     */
    <T> T mandatory(TransactionCallback<T> action);

    /**
     * @see org.springframework.transaction.TransactionDefinition#PROPAGATION_NESTED
     */
    <T> T nested(TransactionCallback<T> action);
}
