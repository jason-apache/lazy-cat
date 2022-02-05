package cool.lazy.cat.orm.core.jdbc;

import cool.lazy.cat.orm.core.jdbc.adapter.ConditionTypeAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.ConditionTypeAdapterImpl;
import cool.lazy.cat.orm.core.jdbc.adapter.DialectAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.DialectAdapterImpl;
import cool.lazy.cat.orm.core.jdbc.adapter.DynamicNameAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.DynamicNameAdapterImpl;
import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapter;
import cool.lazy.cat.orm.core.jdbc.adapter.FunctionHandlerAdapterImpl;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.ConditionTypeMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.InConditionTypeMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.JdbcOperationHolderMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.KeywordConditionMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.LikeConditionMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.NoneConditionMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.NotLikeConditionMapper;
import cool.lazy.cat.orm.core.jdbc.adapter.mapper.NullConditionMapper;
import cool.lazy.cat.orm.core.jdbc.analyzer.ConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultRowAggregator;
import cool.lazy.cat.orm.core.jdbc.analyzer.RowAggregator;
import cool.lazy.cat.orm.core.jdbc.component.executor.TriggerExecutor;
import cool.lazy.cat.orm.core.jdbc.component.handle.SpecialColumnHandler;
import cool.lazy.cat.orm.core.jdbc.component.handle.SpecialColumnHandlerImpl;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.constant.BeanOrderConstant;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapter;
import cool.lazy.cat.orm.core.jdbc.datasource.JdbcOperationHolderAdapterImpl;
import cool.lazy.cat.orm.core.jdbc.datasource.SpringBuiltInJdbcTemplateSupportMapper;
import cool.lazy.cat.orm.core.jdbc.generator.AliasNameGenerator;
import cool.lazy.cat.orm.core.jdbc.generator.impl.DefaultAliasNameGenerator;
import cool.lazy.cat.orm.core.jdbc.provider.IdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.SpecialColumnProvider;
import cool.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultIdGeneratorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultTriggerProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.FastMappingResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.MultiplexedObjectResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.SimpleSpecialColumnProvider;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.MysqlDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.OracleDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.PostgreSqlDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.SimpleMysqlDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.SimpleOracleDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.SimplePostgreSqlDialect;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.CountFunctionHandler;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.FunctionHandler;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.MysqlPostgreSqlConcatFunctionHandler;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.function.OracleConcatFunctionHandler;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.executor.SqlExecutorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.PageSqlInterceptor;
import cool.lazy.cat.orm.core.jdbc.sql.interceptor.SqlInterceptor;
import cool.lazy.cat.orm.core.jdbc.sql.printer.ConditionSqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.DeleteSqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.InsertSqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.SelectSqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.SqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.UpdateSqlPrinter;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.Corrector;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutorImpl;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.DynamicNameNotInitCorrector;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.FunctionDialectNotInitCorrector;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.SqlConditionParameterNotMappedCorrector;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/4/21 19:30
 */
@Configuration
public class JdbcComponentAutoConfiguration {

    @Bean
    @ConditionalOnExpression(value = "!'null'.equals('${spring.datasource.url:null}')")
    @ConditionalOnMissingBean(value = JdbcOperationHolderMapper.class)
    @Order(value = Integer.MAX_VALUE)
    public JdbcOperationHolderMapper springBuiltInJdbcTemplateSupportMapper(
            PlatformTransactionManager platformTransactionManager, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            DialectAdapter dialectAdapter, @Value("${spring.datasource.url}") String jdbcUrl) {
        return new SpringBuiltInJdbcTemplateSupportMapper(platformTransactionManager, namedParameterJdbcTemplate, dialectAdapter, DatabaseDriver.fromJdbcUrl(jdbcUrl));
    }

    @Bean
    @ConditionalOnMissingBean(value = JdbcOperationHolderAdapter.class)
    public JdbcOperationHolderAdapter jdbcOperationHolderAdapter(List<JdbcOperationHolderMapper> jdbcOperationHolderMapperList) {
        return new JdbcOperationHolderAdapterImpl(jdbcOperationHolderMapperList);
    }

    @Bean
    @ConditionalOnMissingBean(value = ConditionAnalyzer.class)
    public DefaultConditionAnalyzer defaultConditionAnalyzer(ConditionTypeAdapter conditionTypeAdapter) {
        return new DefaultConditionAnalyzer(conditionTypeAdapter);
    }

    @Bean
    @ConditionalOnMissingBean(value = RowAggregator.class)
    public DefaultRowAggregator defaultRowAggregator() {
        return new DefaultRowAggregator();
    }

    @Bean
    @ConditionalOnMissingBean
    public SequenceIdGenerator sequenceIdGenerator(PojoTableManager pojoTableManager, DynamicNameAdapter dynamicNameAdapter) {
        return new SequenceIdGenerator(pojoTableManager, dynamicNameAdapter);
    }

    @Bean
    @ConditionalOnMissingBean(value = AliasNameGenerator.class)
    public DefaultAliasNameGenerator defaultAliasNameGenerator() {
        return new DefaultAliasNameGenerator();
    }

    @Bean
    @ConditionalOnExpression(value = "${cool.lazy-cat.jdbc.multiplex-object:true}")
    public MultiplexedObjectResultSetExtractorProvider multiplexedObjectResultSetExtractorProvider(RowAggregator rowAggregator, PojoTableManager pojoTableManager, SpecialColumnProvider specialColumnProvider) {
        return new MultiplexedObjectResultSetExtractorProvider(rowAggregator, pojoTableManager, specialColumnProvider);
    }

    @Bean
    @ConditionalOnMissingBean(value = ResultSetExtractorProvider.class)
    public FastMappingResultSetExtractorProvider fastMappingResultSetExtractorProvider(RowAggregator rowAggregator, PojoTableManager pojoTableManager, SpecialColumnProvider specialColumnProvider) {
        return new FastMappingResultSetExtractorProvider(rowAggregator, pojoTableManager, specialColumnProvider);
    }

    @Bean
    @ConditionalOnMissingBean(value = IdGeneratorProvider.class)
    public DefaultIdGeneratorProvider defaultIdGeneratorProvider() {
        return new DefaultIdGeneratorProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = TriggerProvider.class)
    public DefaultTriggerProvider defaultTriggerProvider() {
        return new DefaultTriggerProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = SpecialColumnProvider.class)
    public SimpleSpecialColumnProvider simpleSpecialColumnProvider() {
        return new SimpleSpecialColumnProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = SpecialColumnHandler.class)
    public SpecialColumnHandler specialColumnHandler(SpecialColumnProvider specialColumnProvider) {
        return new SpecialColumnHandlerImpl(specialColumnProvider);
    }

    @Bean
    @ConditionalOnMissingBean(value = TriggerExecutor.class)
    @Order(value = BeanOrderConstant.COMPONENT_EXECUTOR_BASE_ORDER)
    public TriggerExecutor triggerExecutor(PojoTableManager pojoTableManager, TriggerProvider triggerProvider) {
        return new TriggerExecutor(pojoTableManager, triggerProvider);
    }

    @Bean
    @ConditionalOnMissingBean(value = SqlExecutor.class)
    public SqlExecutor sqlExecutor(JdbcOperationHolderAdapter jdbcOperationHolderAdapter, List<SqlPrinter> sqlPrinterList, List<SqlInterceptor> sqlInterceptorList,
                                   SpecialColumnHandler specialColumnHandler, ResultSetExtractorProvider resultSetExtractorProvider, PojoTableManager pojoTableManager) {
        return new SqlExecutorImpl(jdbcOperationHolderAdapter, sqlPrinterList, sqlInterceptorList, specialColumnHandler, resultSetExtractorProvider, pojoTableManager);
    }

    @Bean
    @ConditionalOnMissingBean(value = DynamicNameAdapter.class)
    public DynamicNameAdapter dynamicNameAdapter(@Autowired(required = false) List<DynamicNameMapper> dynamicNameMapperList) {
        return new DynamicNameAdapterImpl(dynamicNameMapperList);
    }

    @Configuration
    static class SqlTypeConditionMapperConfiguration {
        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER)
        @ConditionalOnMissingBean(value = KeywordConditionMapper.class)
        public KeywordConditionMapper keywordConditionMapping() {
            return new KeywordConditionMapper();
        }

        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER + 10)
        @ConditionalOnMissingBean(value = LikeConditionMapper.class)
        public LikeConditionMapper likeConditionMapping() {
            return new LikeConditionMapper();
        }

        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER + 20)
        @ConditionalOnMissingBean(value = NoneConditionMapper.class)
        public NoneConditionMapper noneConditionMapping() {
            return new NoneConditionMapper();
        }

        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER + 30)
        @ConditionalOnMissingBean(value = InConditionTypeMapper.class)
        public InConditionTypeMapper inConditionTypeMapping() {
            return new InConditionTypeMapper();
        }

        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER + 40)
        @ConditionalOnMissingBean(value = NullConditionMapper.class)
        public NullConditionMapper nullConditionMapping() {
            return new NullConditionMapper();
        }

        @Bean
        @Order(value = BeanOrderConstant.ADAPTER_BASE_ORDER + 50)
        @ConditionalOnMissingBean(value = NotLikeConditionMapper.class)
        public NotLikeConditionMapper notLikeConditionMapper() {
            return new NotLikeConditionMapper();
        }

        @Bean
        @ConditionalOnMissingBean(value = ConditionTypeAdapter.class)
        public ConditionTypeAdapter conditionTypeAdapter(List<ConditionTypeMapper> conditionTypeMapperList) {
            return new ConditionTypeAdapterImpl(conditionTypeMapperList);
        }
    }

    @Configuration
    static class DialectConfiguration {

        @Bean
        @ConditionalOnMissingBean(value = DialectAdapter.class)
        public DialectAdapter dialectAdapter(List<Dialect> dialectList) {
            return new DialectAdapterImpl(dialectList);
        }

        @Bean
        @ConditionalOnMissingBean(value = MysqlDialect.class)
        public SimpleMysqlDialect simpleMysqlDialect(FunctionHandlerAdapter functionHandlerAdapter) {
            return new SimpleMysqlDialect(functionHandlerAdapter);
        }

        @Bean
        @ConditionalOnMissingBean(value = OracleDialect.class)
        public SimpleOracleDialect simpleOracleDialect(FunctionHandlerAdapter functionHandlerAdapter) {
            return new SimpleOracleDialect(functionHandlerAdapter);
        }

        @Bean
        @ConditionalOnMissingBean(value = PostgreSqlDialect.class)
        public SimplePostgreSqlDialect simplePostgreSqlDialect(FunctionHandlerAdapter functionHandlerAdapter) {
            return new SimplePostgreSqlDialect(functionHandlerAdapter);
        }

        @Bean
        @ConditionalOnMissingBean(value = FunctionHandlerAdapter.class)
        public FunctionHandlerAdapter functionHandlerAdapter(List<FunctionHandler> functionHandlerList) {
            return new FunctionHandlerAdapterImpl(functionHandlerList);
        }

        @Bean
        @ConditionalOnMissingBean(value = CountFunctionHandler.class)
        @Order(value = BeanOrderConstant.FUNCTION_HANDLER_BASE_ORDER)
        public CountFunctionHandler countFunctionHandler() {
            return new CountFunctionHandler();
        }

        @Bean
        @ConditionalOnMissingBean(value = MysqlPostgreSqlConcatFunctionHandler.class)
        @Order(value = BeanOrderConstant.FUNCTION_HANDLER_BASE_ORDER + 5)
        public MysqlPostgreSqlConcatFunctionHandler mysqlPostgreSqlConcatFunctionHandler() {
            return new MysqlPostgreSqlConcatFunctionHandler();
        }

        @Bean
        @ConditionalOnMissingBean(value = OracleConcatFunctionHandler.class)
        @Order(value = BeanOrderConstant.FUNCTION_HANDLER_BASE_ORDER + 7)
        public OracleConcatFunctionHandler oracleConcatFunctionHandler() {
            return new OracleConcatFunctionHandler();
        }
    }

    @Configuration
    static class SqlPrinterConfiguration {
        @Bean
        @ConditionalOnMissingBean(value = SelectSqlPrinter.class)
        @Order(value = BeanOrderConstant.PRINTER_BASE_ORDER)
        public SelectSqlPrinter selectSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
            return new SelectSqlPrinter(correctorExecutor, pojoTableManager);
        }

        @Bean
        @ConditionalOnMissingBean(value = InsertSqlPrinter.class)
        @Order(value = BeanOrderConstant.PRINTER_BASE_ORDER + 10)
        public InsertSqlPrinter insertSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
            return new InsertSqlPrinter(correctorExecutor, pojoTableManager);
        }

        @Bean
        @ConditionalOnMissingBean(value = UpdateSqlPrinter.class)
        @Order(value = BeanOrderConstant.PRINTER_BASE_ORDER + 20)
        public UpdateSqlPrinter updateSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
            return new UpdateSqlPrinter( correctorExecutor, pojoTableManager);
        }

        @Bean
        @ConditionalOnMissingBean(value = DeleteSqlPrinter.class)
        @Order(value = BeanOrderConstant.PRINTER_BASE_ORDER + 30)
        public DeleteSqlPrinter deleteSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager) {
            return new DeleteSqlPrinter(correctorExecutor, pojoTableManager);
        }

        @Bean
        @ConditionalOnMissingBean(value = ConditionSqlPrinter.class)
        @Order(value = BeanOrderConstant.PRINTER_BASE_ORDER + 60)
        public ConditionSqlPrinter conditionSqlPrinter(CorrectorExecutor correctorExecutor, PojoTableManager pojoTableManager, ConditionAnalyzer conditionAnalyzer) {
            return new ConditionSqlPrinter(correctorExecutor, pojoTableManager, conditionAnalyzer);
        }

        @Bean
        @ConditionalOnMissingBean(value = CorrectorExecutor.class)
        public CorrectorExecutor correctorExecutor(List<Corrector> correctorList) {
            return new CorrectorExecutorImpl(correctorList);
        }

        @Bean
        @ConditionalOnMissingBean(value = SqlConditionParameterNotMappedCorrector.class)
        @Order(value = BeanOrderConstant.CORRECTOR_BASE_ORDER)
        public SqlConditionParameterNotMappedCorrector sqlConditionParameterNotMappedCorrector() {
            return new SqlConditionParameterNotMappedCorrector();
        }

        @Bean
        @ConditionalOnMissingBean(value = DynamicNameNotInitCorrector.class)
        @Order(value = BeanOrderConstant.CORRECTOR_BASE_ORDER + 10)
        public DynamicNameNotInitCorrector dynamicNameNotInitCorrector(DynamicNameAdapter dynamicNameAdapter) {
            return new DynamicNameNotInitCorrector(dynamicNameAdapter);
        }

        @Bean
        @ConditionalOnMissingBean(value = FunctionDialectNotInitCorrector.class)
        @Order(value = BeanOrderConstant.CORRECTOR_BASE_ORDER + 20)
        public FunctionDialectNotInitCorrector functionDialectNotInitCorrector() {
            return new FunctionDialectNotInitCorrector();
        }

        @Bean
        @ConditionalOnMissingBean(value = PageSqlInterceptor.class)
        @Order(value = BeanOrderConstant.SQL_INTERCEPTOR_BASE_ORDER)
        public PageSqlInterceptor pageSqlInterceptor(CorrectorExecutor correctorExecutor) {
            return new PageSqlInterceptor(correctorExecutor);
        }
    }
}
