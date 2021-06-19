package cool.lazy.cat.orm.core.manager;

import org.springframework.context.annotation.Bean;

/**
 * @author: mahao
 * @date: 2021/3/4 21:18
 */
public class ManagerConfiguration {

    @Bean
    public PojoManager pojoManager() {
        return new PojoManager();
    }

    @Bean
    public RepositoryManager repositoryManager() {
        return new RepositoryManager();
    }

    @Bean
    public ServiceManager serviceManager() {
        return new ServiceManager();
    }

    @Bean
    public BusinessManager businessManager() {
        return new BusinessManager();
    }
}
