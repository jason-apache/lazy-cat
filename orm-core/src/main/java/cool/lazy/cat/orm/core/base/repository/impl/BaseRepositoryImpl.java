package cool.lazy.cat.orm.core.base.repository.impl;

import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.component.executor.ComponentExecutor;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteField;
import cool.lazy.cat.orm.core.jdbc.param.DeleteParamImpl;
import cool.lazy.cat.orm.core.jdbc.param.SearchParamImpl;
import cool.lazy.cat.orm.core.jdbc.param.UpdateParamImpl;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.source.MapSqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.Collections;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 22:00
 */
public class BaseRepositoryImpl extends AbstractBaseRepository implements BaseRepository {

    public BaseRepositoryImpl(SqlExecutor sqlExecutor, PojoTableManager pojoTableManager, IdGeneratorProvider idGeneratorProvider,
                              List<ComponentExecutor> componentExecutorList, JdbcOperationHolderAdapter jdbcOperationHolderAdapter) {
        super(sqlExecutor, pojoTableManager, idGeneratorProvider, componentExecutorList, jdbcOperationHolderAdapter);
    }

    @Override
    public <T> T queryById(Class<T> type, Object id) {
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(type).getTableInfo();
        return this.querySingle(new SearchParamImpl<>(type).setCondition(Condition.eq(tableInfo.getId().getJavaFieldName(), id)));
    }

    @Override
    public <T> List<T> queryByIds(Class<T> type, Object ids) {
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(type).getTableInfo();
        return this.query(new SearchParamImpl<>(type).setCondition(Condition.in(tableInfo.getId().getJavaFieldName(), ids)));
    }

    @Override
    public void update(Object data, boolean cascade, boolean ignoreNull) {
        if (CollectionUtil.sizeOf(data) < 1) {
            return;
        }
        this.update(new UpdateParamImpl(data, cascade).setIgnoreNull(ignoreNull));
    }

    @Override
    public void updateByCondition(SqlSource sqlSource, SqlCondition sqlCondition) {
        this.update(new UpdateParamImpl(sqlSource, sqlCondition));
    }

    @Override
    public void delete(Object data, boolean cascade) {
        if (CollectionUtil.sizeOf(data) < 1) {
            return;
        }
        this.delete(new DeleteParamImpl(data, cascade));
    }

    @Override
    public void deleteById(Class<?> pojoType, Object id) {
        if (CollectionUtil.sizeOf(id) < 1) {
            return;
        }
        this.deleteByCondition(pojoType, Condition.eq(super.pojoTableManager.getByPojoType(pojoType).getTableInfo().getId().getJavaFieldName(), id));
    }

    @Override
    public void deleteByIds(Class<?> pojoType, Object ids) {
        if (CollectionUtil.sizeOf(ids) < 1) {
            return;
        }
        this.deleteByCondition(pojoType, Condition.in(super.pojoTableManager.getByPojoType(pojoType).getTableInfo().getId().getJavaFieldName(), ids));
    }

    @Override
    public void deleteByCondition(Class<?> pojoType, SqlCondition sqlCondition) {
        this.delete(new DeleteParamImpl(pojoType, sqlCondition));
    }

    @Override
    public void logicDeleteById(Class<?> pojoType, Object id) {
        if (CollectionUtil.sizeOf(id) < 1) {
            return;
        }
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(pojoType).getTableInfo();
        this.logicDelete(pojoType, tableInfo.getLogicDeleteField(), Condition.eq(tableInfo.getId().getJavaFieldName(), id));
    }

    @Override
    public void logicDeleteByIds(Class<?> pojoType, Object ids) {
        if (CollectionUtil.sizeOf(ids) < 1) {
            return;
        }
        TableInfo tableInfo = super.pojoTableManager.getByPojoType(pojoType).getTableInfo();
        this.logicDelete(pojoType, tableInfo.getLogicDeleteField(), Condition.in(tableInfo.getId().getJavaFieldName(), ids));
    }

    @Override
    public void logicDeleteByCondition(Class<?> pojoType, SqlCondition sqlCondition) {
        this.logicDelete(pojoType, super.pojoTableManager.getByPojoType(pojoType).getTableInfo().getLogicDeleteField(), sqlCondition);
    }

    protected void logicDelete(Class<?> pojoType, LogicDeleteField logicDeleteField, SqlCondition sqlCondition) {
        if (null == logicDeleteField) {
            logger.warn("pojo不存在逻辑删除字段 #" + pojoType.getName());
            return;
        }
        MapSqlSource sqlSource = new MapSqlSource(pojoType, Collections.singletonMap(logicDeleteField.getJavaFieldName(), logicDeleteField.getDeleteValue()));
        this.updateByCondition(sqlSource, sqlCondition);
    }
}