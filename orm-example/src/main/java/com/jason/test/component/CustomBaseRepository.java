package com.jason.test.component;

import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.repository.impl.BaseRepositoryImpl;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.component.executor.ComponentExecutor;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutor;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/9/23 20:37
 */
@Component
public class CustomBaseRepository extends BaseRepositoryImpl implements BaseRepository {

    public CustomBaseRepository(SqlExecutor sqlExecutor, PojoTableManager pojoTableManager, IdGeneratorProvider idGeneratorProvider,
                                List<ComponentExecutor> componentExecutorList, JdbcOperationHolderAdapter jdbcOperationHolderAdapter) {
        super(sqlExecutor, pojoTableManager, idGeneratorProvider, componentExecutorList, jdbcOperationHolderAdapter);
    }

    @Override
    protected boolean isNewRecord(Object instance) {
        if (instance instanceof BasePojo) {
            // 允许自定义id插入数据库
            if (((BasePojo) instance).isNewRecord()) {
                return true;
            }
        }
        return super.isNewRecord(instance);
    }

}
