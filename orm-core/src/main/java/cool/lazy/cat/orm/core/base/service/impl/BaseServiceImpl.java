package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.base.util.CollectionUtil;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/3/3 17:28
 */
public class BaseServiceImpl<P> extends AbstractBaseService<P> implements BaseService<P> {

    @Override
    public P save(P pojo, boolean cascade) {
        super.getSaveTransactionTemplate(pojo.getClass()).required((s) -> {
            this.baseRepository.save(pojo, cascade);
            return null;
        });
        return pojo;
    }

    @Override
    public void save(Collection<P> pojoCollection, boolean cascade) {
        super.getSaveTransactionTemplate(CollectionUtil.getTypeFromObj(pojoCollection)).required((status -> {
            this.baseRepository.save(pojoCollection, cascade);
            return null;
        }));
    }

    @Override
    public P update(P pojo, boolean cascade, boolean ignoreNull) {
        super.getSaveTransactionTemplate(pojo.getClass()).required((s) -> {
            super.baseRepository.update(pojo, cascade, ignoreNull);
            return null;
        });
        return pojo;
    }

    @Override
    public void update(Collection<P> pojoCollection, boolean cascade, boolean ignoreNull) {
        super.getSaveTransactionTemplate(CollectionUtil.getTypeFromObj(pojoCollection)).required((s) -> {
            super.baseRepository.update(pojoCollection, cascade, ignoreNull);
            return null;
        });
    }

    @Override
    public void delete(P pojo, boolean cascade) {
        super.getDeleteTransactionTemplate(pojo.getClass()).required((s) -> {
            super.baseRepository.delete(pojo);
            return null;
        });
    }

    @Override
    public void delete(Collection<P> pojoCollection, boolean cascade) {
        super.getDeleteByInferTransactionTemplate(CollectionUtil.getTypeFromObj(pojoCollection)).required((s) -> {
            super.baseRepository.delete(pojoCollection);
            return null;
        });
    }

    @Override
    public void logicDelete(P pojo, boolean cascade) {
        super.getLogicDeleteTransactionTemplate(pojo.getClass()).required((s) -> {
            super.baseRepository.logicDelete(pojo);
            return null;
        });
    }

    @Override
    public void logicDelete(Collection<P> pojoCollection, boolean cascade) {
        super.getLogicDeleteTransactionTemplate(CollectionUtil.getTypeFromObj(pojoCollection)).required((s) -> {
            super.baseRepository.logicDelete(pojoCollection);
            return null;
        });
    }

    @Override
    public void logicDeleteByIds(Class<?> pojoType, Collection<?> ids) {
        super.getLogicDeleteTransactionTemplate(pojoType).required((s) -> {
            super.baseRepository.logicDeleteByIds(pojoType, ids);
            return null;
        });
    }

    @Override
    public void deleteByInfer(P pojo, boolean cascade) {
        super.getDeleteByInferTransactionTemplate(pojo.getClass()).required((s) -> {
            super.baseRepository.deleteByInfer(pojo, cascade);
            return null;
        });
    }

    @Override
    public void deleteByInfer(Collection<P> pojoCollection, boolean cascade) {
        super.getDeleteByInferTransactionTemplate(CollectionUtil.getTypeFromObj(pojoCollection)).required((s) -> {
            super.baseRepository.deleteByInfer(pojoCollection, cascade);
            return null;
        });
    }

    @Override
    public void deleteByIds(Class<?> pojoType, Collection<?> ids) {
        super.getDeleteTransactionTemplate(pojoType).required((s) -> {
            super.baseRepository.deleteByIds(pojoType, ids);
            return null;
        });
    }

    @Override
    public void deleteByIdsAndInfer(Class<?> pojoType, Collection<?> ids) {
        super.getDeleteByInferTransactionTemplate(pojoType).required((s) -> {
            super.baseRepository.deleteByIdsAndInfer(pojoType, ids);
            return null;
        });
    }
}
