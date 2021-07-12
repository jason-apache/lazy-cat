package cool.lazy.cat.orm.api.web.entrust.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.DefaultExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.DefaultResponseDataWriterHolder;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ResponseDataWriterHolder;
import cool.lazy.cat.orm.api.web.entrust.executor.resolver.JsonArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.executor.resolver.MethodArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.executor.resolver.RequestParameterArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.executor.resolver.ServletRequestArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.executor.resolver.ServletResponseArgumentResolver;
import cool.lazy.cat.orm.api.web.entrust.executor.writer.JsonResponseDataWriter;
import cool.lazy.cat.orm.api.web.entrust.executor.writer.ResponseDataWriter;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/8 13:21
 */
public class ExecutorConfiguration {

    @Bean
    @Order(value = ApiConstant.ARGUMENT_RESOLVER_BASE_ORDER)
    public JsonArgumentResolver jsonArgumentResolver(ObjectMapper objectMapper) {
        return new JsonArgumentResolver(objectMapper);
    }

    @Bean
    @Order(value = ApiConstant.ARGUMENT_RESOLVER_BASE_ORDER + 10)
    public ServletRequestArgumentResolver servletRequestArgumentResolver() {
        return new ServletRequestArgumentResolver();
    }

    @Bean
    @Order(value = ApiConstant.ARGUMENT_RESOLVER_BASE_ORDER + 11)
    public ServletResponseArgumentResolver servletResponseArgumentResolver() {
        return new ServletResponseArgumentResolver();
    }

    @Bean
    @Order(Integer.MAX_VALUE)
    public RequestParameterArgumentResolver requestParameterArgumentResolver() {
        return new RequestParameterArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiMethodParameterInjector.class)
    public DefaultApiMethodParameterInjector defaultApiMethodParameterInjector(List<MethodArgumentResolver> argumentResolverList) {
        return new DefaultApiMethodParameterInjector(argumentResolverList);
    }

    @Bean
    @ConditionalOnMissingBean(value = ResponseDataWriter.class)
    @Order(value = Integer.MAX_VALUE)
    public JsonResponseDataWriter jsonResponseDataWriter(ObjectMapper objectMapper) {
        return new JsonResponseDataWriter(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = ResponseDataWriterHolder.class)
    public DefaultResponseDataWriterHolder defaultResponseDataWriter(List<ResponseDataWriter> writerList) {
        return new DefaultResponseDataWriterHolder(writerList);
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiMethodExecutor.class)
    public DefaultApiMethodExecutor defaultApiMethodExecutor(ApiMethodParameterInjector apiMethodParameterInjector, ResponseDataWriterHolder responseDataWriterHolder, List<ApiMethodEntry> apiMethodEntryList) {
        return new DefaultApiMethodExecutor(apiMethodParameterInjector, responseDataWriterHolder, apiMethodEntryList);
    }

    @Bean
    @ConditionalOnMissingBean(value = ExecutorHolder.class)
    public DefaultExecutorHolder defaultExecutorHolder(ApiMethodExecutor apiMethodExecutor) {
        return new DefaultExecutorHolder(apiMethodExecutor);
    }
}
