package cool.lazy.cat.orm.core.jdbc;

import cool.lazy.cat.orm.core.jdbc.analyzer.ConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultExpressionAdapter;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultFieldInfoCatcher;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultParameterInjector;
import cool.lazy.cat.orm.core.jdbc.analyzer.DefaultRowAggregator;
import cool.lazy.cat.orm.core.jdbc.analyzer.ExpressionAdapter;
import cool.lazy.cat.orm.core.jdbc.analyzer.FieldInfoCatcher;
import cool.lazy.cat.orm.core.jdbc.analyzer.ParameterInjector;
import cool.lazy.cat.orm.core.jdbc.analyzer.RowAggregator;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.dialect.DialectRegister;
import cool.lazy.cat.orm.core.jdbc.dialect.SimpleMysqlDialect;
import cool.lazy.cat.orm.core.jdbc.dialect.SimpleOracleDialect;
import cool.lazy.cat.orm.core.jdbc.generator.AliasNameGenerator;
import cool.lazy.cat.orm.core.jdbc.generator.impl.SimpleAliasNameGenerator;
import cool.lazy.cat.orm.core.jdbc.handle.DefaultValidateHandler;
import cool.lazy.cat.orm.core.jdbc.handle.ValidateHandler;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import cool.lazy.cat.orm.core.jdbc.manager.factory.PojoTableSubjectFactory;
import cool.lazy.cat.orm.core.jdbc.provider.IdProvider;
import cool.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.SqlParamProvider;
import cool.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import cool.lazy.cat.orm.core.jdbc.provider.TypeConverterProvider;
import cool.lazy.cat.orm.core.jdbc.provider.ValidatorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultIdProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultSqlParamProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultTriggerProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultTypeConverterProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.DefaultValidatorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.impl.FastMappingResultSetExtractorProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/4/21 19:30
 */
public class JdbcComponentConfiguration {

    @Bean
    public KeyWordConverter keyWordConvert() {
        return new KeyWordConverter();
    }

    @Bean
    @ConditionalOnMissingBean(value = ConditionAnalyzer.class)
    public DefaultConditionAnalyzer defaultConditionAnalyzer() {
        return new DefaultConditionAnalyzer();
    }

    @Bean
    @ConditionalOnMissingBean(value = ExpressionAdapter.class)
    public DefaultExpressionAdapter defaultExpressionAdapter() {
        return new DefaultExpressionAdapter();
    }

    @Bean
    @ConditionalOnMissingBean(value = FieldInfoCatcher.class)
    public DefaultFieldInfoCatcher defaultFieldInfoCatcher() {
        return new DefaultFieldInfoCatcher();
    }

    @Bean
    @ConditionalOnMissingBean(value = ParameterInjector.class)
    public DefaultParameterInjector defaultParameterInjector() {
        return new DefaultParameterInjector();
    }

    @Bean
    @ConditionalOnMissingBean(value = RowAggregator.class)
    public DefaultRowAggregator defaultRowAggregator() {
        return new DefaultRowAggregator();
    }

    @Bean
    @ConditionalOnMissingBean
    public SequenceIdGenerator sequenceIdGenerator() {
        return new SequenceIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(value = DialectRegister.class)
    public DialectRegister dialectRegister() {
        return new DialectRegister();
    }

    @Bean
    @ConditionalOnMissingBean(value = SimpleMysqlDialect.class)
    public SimpleMysqlDialect simpleMysqlDialect() {
        return new SimpleMysqlDialect();
    }

    @Bean
    @ConditionalOnMissingBean(value = SimpleOracleDialect.class)
    public SimpleOracleDialect simpleOracleDialect() {
        return new SimpleOracleDialect();
    }

    @Bean
    @ConditionalOnMissingBean(value = AliasNameGenerator.class)
    public SimpleAliasNameGenerator simpleAliasNameGenerator() {
        return new SimpleAliasNameGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(value = ValidateHandler.class)
    public DefaultValidateHandler defaultValidateHandle() {
        return new DefaultValidateHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = PojoTableSubjectFactory.class)
    public PojoTableSubjectFactory pojoTableSubjectFactory() {
        return new PojoTableSubjectFactory();
    }

    @Bean
    @ConditionalOnMissingBean(value = PojoTableManager.class)
    public PojoTableManager pojoTableManager() {
        return new PojoTableManager();
    }

    @Bean
    @ConditionalOnMissingBean(value = IdProvider.class)
    public DefaultIdProvider defaultIdProvider() {
        return new DefaultIdProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = ResultSetExtractorProvider.class)
    public FastMappingResultSetExtractorProvider fastMappingResultSetExtractorProvider() {
        return new FastMappingResultSetExtractorProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = SqlParamProvider.class)
    public DefaultSqlParamProvider defaultSqlParamProvider() {
        return new DefaultSqlParamProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = TriggerProvider.class)
    public DefaultTriggerProvider defaultTriggerProvider() {
        return new DefaultTriggerProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = TypeConverterProvider.class)
    public DefaultTypeConverterProvider defaultTypeConverterProvider() {
        return new DefaultTypeConverterProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = ValidatorProvider.class)
    public DefaultValidatorProvider defaultValidatorProvider() {
        return new DefaultValidatorProvider();
    }
}
