package cool.lazy.cat.orm.core.jdbc.component.id;

import cool.lazy.cat.orm.base.component.IdGenerator;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.adapter.DynamicNameAdapter;
import cool.lazy.cat.orm.core.jdbc.exception.SequenceNotFoundException;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.sql.ObjectName;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.string.DynamicNameSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.Insert;
import cool.lazy.cat.orm.core.jdbc.util.SqlStringJoinerHelper;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/30 20:01
 * 序列id生成器
 */
public class SequenceIdGenerator implements IdGenerator {

    protected final PojoTableManager pojoTableManager;
    protected final DynamicNameAdapter dynamicNameAdapter;

    public SequenceIdGenerator(PojoTableManager pojoTableManager, DynamicNameAdapter dynamicNameAdapter) {
        this.pojoTableManager = pojoTableManager;
        this.dynamicNameAdapter = dynamicNameAdapter;
    }

    @Override
    public List<Object> generator(List<Object> instances) {
        Class<?> pojoType = CollectionUtil.getTypeFromObj(instances);
        IdField id = pojoTableManager.getByPojoType(pojoType).getTableInfo().getId();
        if (null == id.getParameterValue(Constant.SEQUENCE_NAME)) {
            throw new SequenceNotFoundException("pojo主键未定义序列：#" + pojoType.getName());
        }
        return generator(instances, id);
    }

    /**
     * 根据数据库方言生成序列查询语句，执行查询
     * @param instances 参数
     * @param id id生成策略
     * @return 序列id结果集
     */
    protected List<Object> generator(List<Object> instances, IdField id) {
        JdbcOperationHolder jdbcOperationHolder = JdbcOperationSupport.getAndCheck();
        JdbcOperations jdbcOperations = jdbcOperationHolder.getJdbcOperations();
        Dialect dialect = jdbcOperationHolder.getDialect();
        List<Object> ids = new ArrayList<>(instances.size());
        Class<?> pojoType = CollectionUtil.getTypeFromObj(instances);
        for (int i = 0; i < instances.size(); i++) {
            List<SqlString> sqlStrings = dialect.selectSequenceNextValueSql(pojoType ,id.getParameterValue(Constant.SEQUENCE_SCHEMA), id.getParameterValue(Constant.SEQUENCE_NAME));
            for (SqlString sqlString : sqlStrings) {
                if (sqlString instanceof DynamicNameSqlString) {
                    DynamicNameSqlString dynamicNameSql = (DynamicNameSqlString) sqlString;
                    ObjectName objectName = dynamicNameAdapter.adapt(Insert.class, dynamicNameSql.getPojoType(), dynamicNameSql.getSchema(), dynamicNameSql.getName());
                    dynamicNameSql.setSchema(objectName.getSchema());
                    dynamicNameSql.setName(objectName.getName());
                }
            }
            ids.add(jdbcOperations.queryForObject(SqlStringJoinerHelper.join(sqlStrings, jdbcOperationHolder.getDialect().getDefaultCharacterCase()), id.getJavaType()));
        }
        return ids;
    }
}
