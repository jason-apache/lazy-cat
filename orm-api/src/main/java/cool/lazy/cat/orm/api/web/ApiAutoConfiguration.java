package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.web.entrust.controller.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.controller.EntrustController;
import cool.lazy.cat.orm.api.web.entrust.controller.UnKnowApiEntrustController;
import cool.lazy.cat.orm.api.web.entrust.handle.ApiExceptionHandler;
import cool.lazy.cat.orm.api.web.entrust.handle.BasicExceptionHandler;
import cool.lazy.cat.orm.api.web.entrust.handle.DefaultEntrustForwardHandle;
import cool.lazy.cat.orm.api.web.entrust.handle.EntrustForwardHandle;
import cool.lazy.cat.orm.api.web.entrust.handle.FrameworkExceptionHandler;
import cool.lazy.cat.orm.api.web.entrust.handle.ServletExceptionHandler;
import cool.lazy.cat.orm.api.web.provider.ApiPathProvider;
import cool.lazy.cat.orm.api.web.provider.ApiPojoSubjectProvider;
import cool.lazy.cat.orm.api.web.provider.DefaultApiPathProvider;
import cool.lazy.cat.orm.api.web.provider.DefaultApiPojoSubjectProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author: mahao
 * @date: 2021/4/21 19:56
 */
@Import(value = ApiAutoConfiguration.RemoveFrameworkBean.class)
public class ApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = EntrustApi.class)
    public EntrustController entrustController() {
        return new EntrustController();
    }

    @Bean
    public UnKnowApiEntrustController unKnowApiEntrustController(RemoveFrameworkBean removeFrameworkBean, ServerProperties serverProperties) {
        return new UnKnowApiEntrustController(serverProperties);
    }

    @Bean
    @ConditionalOnMissingBean(value = EntrustForwardHandle.class)
    public DefaultEntrustForwardHandle defaultEntrustForwardHandle() {
        return new DefaultEntrustForwardHandle();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiPathProvider.class)
    public DefaultApiPathProvider defaultApiPathProvider() {
        return new DefaultApiPathProvider();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiPojoSubjectProvider.class)
    public DefaultApiPojoSubjectProvider defaultApiPojoSubjectProvider() {
        return new DefaultApiPojoSubjectProvider();
    }

    @Bean
    public UriPojoMapping uriPojoMapping() {
        return new UriPojoMapping();
    }

    public static class RemoveFrameworkBean implements BeanDefinitionRegistryPostProcessor {
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
            String targetBeanName = "basicErrorController";
            if (beanDefinitionRegistry.containsBeanDefinition(targetBeanName)){
                beanDefinitionRegistry.removeBeanDefinition(targetBeanName);
            }

        }
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        }
    }

    @Bean
    @ConditionalOnMissingBean(value = BasicExceptionHandler.class)
    public BasicExceptionHandler basicExceptionHandle() {
        return new BasicExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = ServletExceptionHandler.class)
    public ServletExceptionHandler servletExceptionHandler() {
        return new ServletExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = FrameworkExceptionHandler.class)
    public FrameworkExceptionHandler frameworkExceptionHandler() {
        return new FrameworkExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = ApiExceptionHandler.class)
    public ApiExceptionHandler apiExceptionHandler() {
        return new ApiExceptionHandler();
    }
}
