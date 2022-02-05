package cool.lazy.cat.orm.core.jdbc.sql.interceptor;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.MetaInfoImpl;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructureImpl;
import cool.lazy.cat.orm.core.jdbc.sql.type.Select;
import cool.lazy.cat.orm.core.jdbc.util.SqlStringJoinerHelper;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/23 10:22
 */
public class PageSqlInterceptor implements SqlInterceptor {

    public static final String TOTAL_COUNT_KEY = PageSqlInterceptor.class.getName() + ":totalCount";
    protected final CorrectorExecutor correctorExecutor;

    public PageSqlInterceptor(CorrectorExecutor correctorExecutor) {
        this.correctorExecutor = correctorExecutor;
    }

    @Override
    public boolean intercept(SqlParameterMapping sqlParameterMapping) {
        return Select.class.isAssignableFrom(sqlParameterMapping.getType()) && sqlParameterMapping.getParam() instanceof SearchParam && ((SearchParam<?>) sqlParameterMapping.getParam()).needPaging();
    }

    @Override
    public void preExecute(SqlParameterMapping sqlParameterMapping) {
        JdbcOperationHolder jdbcOperationHolder = JdbcOperationSupport.getAndCheck();
        // 取得当前处理的sql参数
        ParameterMapping currentlyProcessed = sqlParameterMapping.getCurrentlyProcessed();
        DQLSqlStructure originalSqlStructure = (DQLSqlStructure) currentlyProcessed.getSqlStructure();
        // 通过方言得出分页查询语句
        List<SqlString> limitSql = jdbcOperationHolder.getDialect().limitSql((SearchParam<?>) sqlParameterMapping.getParam(), originalSqlStructure);
        if (sqlParameterMapping.getMetaInfo() == null) {
            sqlParameterMapping.setMetaInfo(new MetaInfoImpl());
        }
        // 将总数放入元信息
        sqlParameterMapping.getMetaInfo().set(TOTAL_COUNT_KEY, this.getTotalCount(jdbcOperationHolder, sqlParameterMapping, originalSqlStructure));
        // 替换sql结构 注入新的携带分页属性的sql
        DQLSqlStructureImpl dqlSqlStructure = new DQLSqlStructureImpl();
        dqlSqlStructure.setBehaviorDescriptor(originalSqlStructure.getBehaviorDescriptor());
        dqlSqlStructure.setFromSqlString(originalSqlStructure.getFrom());
        dqlSqlStructure.setWhere(originalSqlStructure.getWhere());
        dqlSqlStructure.setOrderBy(originalSqlStructure.getOrderBy());
        limitSql.forEach(l -> {
            correctorExecutor.correcting(l, sqlParameterMapping);
            dqlSqlStructure.addSqlString(l);
        });
        currentlyProcessed.setSqlStructure(dqlSqlStructure);
    }

    /**
     * 得出总行数
     * @param jdbcOperationHolder jdbc操作对象
     * @param sqlParameterMapping sql参数
     * @param originalSqlStructure 原始sql查询结构
     * @return 行总数
     */
    protected Long getTotalCount(JdbcOperationHolder jdbcOperationHolder, SqlParameterMapping sqlParameterMapping, DQLSqlStructure originalSqlStructure) {
        List<SqlString> countSql = jdbcOperationHolder.getDialect().countSql((SearchParam<?>) sqlParameterMapping.getParam(), originalSqlStructure);
        countSql.forEach(c -> correctorExecutor.correcting(c, sqlParameterMapping));
        return jdbcOperationHolder.getNamedParameterJdbcTemplate()
                .queryForObject(SqlStringJoinerHelper.join(countSql, jdbcOperationHolder.getDialect().getDefaultCharacterCase()),
                        sqlParameterMapping.getCurrentlyProcessed().getSingleSqlSource(), Long.class);

    }
}
