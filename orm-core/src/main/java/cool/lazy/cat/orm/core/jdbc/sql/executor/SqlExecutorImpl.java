package cool.lazy.cat.orm.core.jdbc.sql.executor;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.component.handle.SpecialColumnHandler;
import cool.lazy.cat.orm.core.jdbc.param.DataHolderParam;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.sql.DataInfo;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMappingImpl;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.SqlInterceptor;
import cool.lazy.cat.orm.core.jdbc.sql.printer.SqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/20 11:04
 */
public class SqlExecutorImpl extends AbstractSqlExecutor implements SqlExecutor {

    protected final ResultSetExtractorProvider resultSetExtractorProvider;
    protected final PojoTableManager pojoTableManager;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SqlExecutorImpl(JdbcOperationHolderAdapter jdbcOperationHolderAdapter, List<SqlPrinter> sqlPrinterList, List<SqlInterceptor> sqlInterceptorList,
                           SpecialColumnHandler specialColumnHandler, ResultSetExtractorProvider resultSetExtractorProvider, PojoTableManager pojoTableManager) {
        super(jdbcOperationHolderAdapter, sqlPrinterList, sqlInterceptorList, specialColumnHandler);
        this.resultSetExtractorProvider = resultSetExtractorProvider;
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public <T> DataInfo<T> query(SearchParam<T> searchParam) {
        SqlParameterMappingImpl sqlParameterMapping = new SqlParameterMappingImpl(Select.class, searchParam, null);
        JdbcOperationHolder jdbcOperationHolder = super.adapterJdbcTemplate(sqlParameterMapping.getType(), sqlParameterMapping.getParam().getPojoType());
        try {
            NamedParameterJdbcTemplate jdbcTemplate = jdbcOperationHolder.getNamedParameterJdbcTemplate();
            super.process(sqlParameterMapping);
            String sql = sqlParameterMapping.getCurrentlyProcessed().getSqlStructure().toSqlString(jdbcOperationHolder.getDialect().getDefaultCharacterCase());
            List<T> metaData = jdbcTemplate.query(sql, sqlParameterMapping.getCurrentlyProcessed().getSingleSqlSource(), resultSetExtractorProvider.provider(sqlParameterMapping));
            jdbcOperationHolder.release();
            return new DataInfo<>(sqlParameterMapping.getMetaInfo(), metaData);
        } finally {
            JdbcOperationSupport.clear();
        }
    }

    @Override
    public void save(Class<? extends SqlType> sqlType, DataHolderParam dataHolderParam, DataOperationDescriptor operationDescriptor) {
        if (null == dataHolderParam || null == operationDescriptor) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        SqlParameterMappingImpl sqlParameterMapping = new SqlParameterMappingImpl(sqlType, dataHolderParam, operationDescriptor);
        JdbcOperationHolder jdbcOperationHolder = super.adapterJdbcTemplate(sqlParameterMapping.getType(), sqlParameterMapping.getParam().getPojoType());
        try {
            NamedParameterJdbcTemplate jdbcTemplate = jdbcOperationHolder.getNamedParameterJdbcTemplate();
            super.process(sqlParameterMapping);
            for (ParameterMapping mapping : sqlParameterMapping.getParameterMappings()) {
                String sqlString = mapping.getSqlStructure().toSqlString(jdbcOperationHolder.getDialect().getDefaultCharacterCase());
                if (CollectionUtil.isNotEmpty(mapping.getSqlSources())) {
                    if (mapping.getSqlSources().size() > 1) {
                        jdbcTemplate.batchUpdate(sqlString, mapping.getSqlSources().toArray(new SqlSource[0]));
                    } else {
                        jdbcTemplate.update(sqlString, mapping.getSingleSqlSource());
                    }
                } else {
                    jdbcTemplate.getJdbcTemplate().update(sqlString);
                }
            }
            jdbcOperationHolder.release();
        } finally {
            JdbcOperationSupport.clear();
        }
    }
}
