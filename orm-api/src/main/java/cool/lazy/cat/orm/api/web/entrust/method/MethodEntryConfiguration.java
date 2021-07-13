package cool.lazy.cat.orm.api.web.entrust.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/7/12 13:41
 */
public class MethodEntryConfiguration {

    @Bean
    public QueryApiEntry queryApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new QueryApiEntry(api, objectMapper);
    }

    @Bean
    public QueryPageApiEntry queryPageApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new QueryPageApiEntry(api, objectMapper);
    }

    @Bean
    public RemoveApiEntry removeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveApiEntry(api, objectMapper);
    }

    @Bean
    public RemoveByIdsApiEntry removeByIdsApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveByIdsApiEntry(api, objectMapper);
    }

    @Bean
    public RemoveCascadeApiEntry removeCascadeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new RemoveCascadeApiEntry(api, objectMapper);
    }

    @Bean
    public SaveApiEntry saveApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new SaveApiEntry(api, objectMapper);
    }

    @Bean
    public SaveCascadeApiEntry saveCascadeApiEntry(@Qualifier(value = "entrustApi") EntrustApi api, ObjectMapper objectMapper) {
        return new SaveCascadeApiEntry(api, objectMapper);
    }
}
