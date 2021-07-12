package cool.lazy.cat.orm.api.web.entrust.method;

import cool.lazy.cat.orm.api.web.entrust.EntrustApi;
import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/7/12 13:41
 */
public class MethodEntryConfiguration {

    @Bean
    public QueryApiEntry queryApiEntry(EntrustApi api) {
        return new QueryApiEntry(api);
    }

    @Bean
    public QueryPageApiEntry queryPageApiEntry(EntrustApi api) {
        return new QueryPageApiEntry(api);
    }

    @Bean
    public RemoveApiEntry removeApiEntry(EntrustApi api) {
        return new RemoveApiEntry(api);
    }

    @Bean
    public RemoveByIdsApiEntry removeByIdsApiEntry(EntrustApi api) {
        return new RemoveByIdsApiEntry(api);
    }

    @Bean
    public RemoveCascadeApiEntry removeCascadeApiEntry(EntrustApi api) {
        return new RemoveCascadeApiEntry(api);
    }

    @Bean
    public SaveApiEntry saveApiEntry(EntrustApi api) {
        return new SaveApiEntry(api);
    }

    @Bean
    public SaveCascadeApiEntry saveCascadeApiEntry(EntrustApi api) {
        return new SaveCascadeApiEntry(api);
    }
}
