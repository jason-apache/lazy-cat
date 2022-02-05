package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.service.CommonApiService;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import cool.lazy.cat.orm.api.web.entrust.EntrustApiImpl;
import cool.lazy.cat.orm.core.manager.ServiceManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/7/12 13:41
 * api方法注册
 */
public class MethodEntryConfiguration {

    @Bean
    public EntrustApiImpl entrustApi(ServiceManager serviceManager, CommonApiService commonApiService) {
        return new EntrustApiImpl(serviceManager, commonApiService);
    }

    @Bean
    @ConditionalOnMissingBean(value = QueryApiEntry.class)
    public QueryApiEntry queryApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new QueryApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = QueryPageApiEntry.class)
    public QueryPageApiEntry queryPageApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new QueryPageApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = RemoveApiEntry.class)
    public RemoveApiEntry removeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = RemoveByIdsApiEntry.class)
    public RemoveByIdsApiEntry removeByIdsApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveByIdsApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = RemoveCascadeApiEntry.class)
    public RemoveCascadeApiEntry removeCascadeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveCascadeApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = SaveApiEntry.class)
    public SaveApiEntry saveApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new SaveApiEntry(api, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(value = SaveCascadeApiEntry.class)
    public SaveCascadeApiEntry saveCascadeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new SaveCascadeApiEntry(api, objectMapper);
    }
}
