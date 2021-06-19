package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.util.BeanFieldUtil;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.InvokeHelper;
import cool.lazy.cat.orm.core.jdbc.DataOperationType;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.component.id.IdGenerator;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import cool.lazy.cat.orm.core.jdbc.mapping.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/3 17:28
 */
@Transactional
public class BaseServiceImpl<P> extends AbstractService<P> implements BaseService<P> {

    @Override
    public Collection<P> selectByParam(SearchParam<P> searchParam) {
        return this.selectByParam(Caster.cast(tryGetPojoType()), searchParam);
    }

    @Override
    public <T> Collection<T> selectByParam(Class<T> pojoType, SearchParam<T> searchParam) {
        return super.getRepository(pojoType).query(searchParam);
    }

    @Override
    public PageResult<P> selectPageByParam(SearchParam<P> searchParam) {
        return this.selectPageByParam(Caster.cast(tryGetPojoType()), searchParam);
    }

    @Override
    public <T> PageResult<T> selectPageByParam(Class<T> pojoType, SearchParam<T> searchParam) {
        return super.getRepository(pojoType).queryPage(searchParam);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<P> select(QueryInfo queryInfo) {
        super.initCondition(queryInfo);
        return (Collection<P>) super.getRepository(tryGetPojoType()).selectByInfo(queryInfo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageResult<P> selectPage(QueryInfo queryInfo) {
        super.initCondition(queryInfo);
        return (PageResult<P>) super.getRepository(tryGetPojoType()).selectPage(queryInfo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public P selectById(Object id) {
        return (P) this.selectById(tryGetPojoType(), id);
    }

    @Override
    public <T> T selectById(Class<T> pojoType, Object id) {
        return super.getRepository(pojoType).selectSingle(pojoType, Condition.eq(super.getPojoIdName(pojoType), id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<P> selectByIds(List<Object> ids) {
        return (Collection<P>) this.selectByIds(tryGetPojoType(), ids);
    }

    @Override
    public <T> Collection<T> selectByIds(Class<T> pojoType, List<Object> ids) {
        return super.getRepository(pojoType).select(pojoType, Condition.in(super.getPojoIdName(pojoType), ids));
    }

    @Override
    public P save(P pojo, boolean cascade) {
        if (null == super.getPojoId(pojo)) {
            return this.insert(pojo, cascade);
        } else {
            return this.update(pojo, cascade, false);
        }
    }

    @Override
    public Collection<P> batchSave(Collection<P> pojoCollection, boolean cascade) {
        List<P> addData = pojoCollection.stream().filter(p -> null == super.getPojoId(p)).collect(Collectors.toList());
        List<P> modifyData = pojoCollection.stream().filter(p -> null != super.getPojoId(p)).collect(Collectors.toList());
        this.batchInsert(addData, cascade);
        this.batchUpdate(modifyData, cascade, false);
        addData.addAll(modifyData);
        return addData;
    }

    @Override
    public P insert(P pojo, boolean cascade) {
        IdStrategy id = pojoTableManager.getByPojoType(pojo.getClass()).getTableInfo().getId();
        Class<? extends IdGenerator> idGenerator = id.getIdGenerator();
        if (idGenerator == Auto.class) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            super.getRepository(pojo.getClass()).insert(Caster.cast(pojo), keyHolder);
            InvokeHelper.invokeSetter(id.getSetter(), pojo, BeanFieldUtil.typeAdapter(id.getJavaType(), keyHolder.getKey()));
        } else {
            super.getRepository(pojo.getClass()).insert(Caster.cast(pojo));
        }
        super.dataOperation(DataOperationType.INSERT, pojo, cascade);
        return pojo;
    }

    @Override
    public Collection<P> batchInsert(Collection<P> pojoCollection, boolean cascade) {
        Class<?> pojoType = BeanFieldUtil.getTypeFromObj(pojoCollection);
        super.getRepository(pojoType).batchInsert(Caster.cast(pojoCollection));
        if (cascade) {
            pojoCollection.forEach(p -> super.dataOperation(DataOperationType.INSERT, p, true));
        }
        return pojoCollection;
    }

    @Override
    public P update(P pojo, boolean cascade, boolean ignoreNull) {
        super.getRepository(pojo.getClass()).update(Caster.cast(pojo), Condition.eq(super.getPojoIdName(pojo), super.getPojoId(pojo)), ignoreNull);
        super.dataOperation(DataOperationType.UPDATE, pojo, cascade);
        return pojo;
    }

    @Override
    public Collection<P> batchUpdate(Collection<P> pojoCollection, boolean cascade, boolean ignoreNull) {
        pojoCollection.forEach(p -> this.update(p, cascade, ignoreNull));
        return pojoCollection;
    }

    @Override
    public void delete(P pojo, boolean cascade) {
        if (null == super.getPojoId(pojo)) {
            return;
        }
        super.getRepository(pojo.getClass()).delete(pojo.getClass(), Condition.eq(super.getPojoIdName(pojo), super.getPojoId(pojo)));
        super.dataOperation(DataOperationType.DELETE, pojo, cascade);
    }

    @Override
    public void batchDelete(Collection<P> pojoCollection, boolean cascade) {
        if (CollectionUtil.isNotEmpty(pojoCollection)) {
            Class<?> pojoType = BeanFieldUtil.getTypeFromObj(pojoCollection);
            TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
            IdStrategy id = tableInfo.getId();
            List<Object> ids = pojoCollection.stream().filter(Objects::nonNull).map(p -> InvokeHelper.invokeGetter(id.getGetter(), p))
                    .filter(Objects::nonNull).collect(Collectors.toList());
            super.getRepository(pojoType).delete(pojoType, Condition.in(id.getJavaFieldName(), ids));
            if (cascade) {
                pojoCollection.forEach(p -> super.dataOperation(DataOperationType.DELETE, p, cascade));
            }
        }
    }

    @Override
    public void logicDelete(P pojo, boolean cascade) {
        Object id = super.getPojoId(pojo);
        if (null == id) {
            return;
        }
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(pojo.getClass()).getTableInfo();
        LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
        if (null != logicDeleteField) {
            super.getRepository(pojo.getClass()).logicDelete(Caster.cast(pojo), Condition.eq(super.getPojoIdName(pojo), super.getPojoId(pojo)));
            super.dataOperation(DataOperationType.LOGIC_DELETE, pojo, cascade);
        }
    }

    @Override
    public void batchLogicDelete(Collection<P> pojoCollection, boolean cascade) {
        pojoCollection.forEach(p -> this.logicDelete(p, cascade));
    }

    @Override
    public void deleteByInfer(P pojo, boolean cascade) {
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(pojo.getClass()).getTableInfo();
        if (null != tableInfo.getLogicDeleteField()) {
            this.logicDelete(pojo, cascade);
        } else {
            this.delete(pojo, cascade);
        }
    }

    @Override
    public void batchDeleteByInfer(Collection<P> pojoCollection, boolean cascade) {
        if (CollectionUtil.isNotEmpty(pojoCollection)) {
            TableInfo tableInfo = super.pojoTableManager.getByPojoType(BeanFieldUtil.getTypeFromObj(pojoCollection)).getTableInfo();
            if (null != tableInfo.getLogicDeleteField()) {
                this.batchLogicDelete(pojoCollection, cascade);
            } else {
                this.batchDelete(pojoCollection, cascade);
            }
        }
    }

    @Override
    public <T> void deleteByIds(Class<T> pojoType, Collection<?> ids) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        IdStrategy id = tableInfo.getId();
        super.getRepository(pojoType).getJdbcTemplate().update("delete from " + tableInfo.getFullName() + " where " + id.getDbFieldName() + " in (:ids)",
                Collections.singletonMap("ids", ids));
    }

    @Override
    public <T> void deleteByIdsAndInfer(Class<T> pojoType, Collection<?> ids) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
        if (null == logicDeleteField) {
            this.deleteByIds(pojoType, ids);
        } else {
            IdStrategy id = tableInfo.getId();
            Map<String, Object> param = new HashMap<>(2);
            param.put("ids", ids);
            param.put("deleteValue", logicDeleteField.getDeleteValue());
            super.getRepository(pojoType).getJdbcTemplate().update("update " + tableInfo.getFullName() + " set " + logicDeleteField.getDbFieldName() + " =:deleteValue where " + id.getDbFieldName() + " in (:ids)",
                    param);
        }
    }
}
