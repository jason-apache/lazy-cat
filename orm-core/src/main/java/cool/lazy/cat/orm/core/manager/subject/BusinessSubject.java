package cool.lazy.cat.orm.core.manager.subject;

/**
 * @author: mahao
 * @date: 2021/3/4 20:21
 * 业务主体对象，包含service、repository
 */
public class BusinessSubject implements Subject {

    private PojoSubject pojoSubject;
    private ServiceSubject serviceSubject;
    private RepositorySubject repositorySubject;

    public void setPojoType(PojoSubject pojoSubject) {
        this.pojoSubject = pojoSubject;
    }

    public void setServiceSubject(ServiceSubject serviceSubject) {
        this.serviceSubject = serviceSubject;
    }

    public void setRepositorySubject(RepositorySubject repositorySubject) {
        this.repositorySubject = repositorySubject;
    }

    public PojoSubject getPojoSubject() {
        return pojoSubject;
    }

    public ServiceSubject getServiceSubject() {
        return serviceSubject;
    }

    public RepositorySubject getRepositorySubject() {
        return repositorySubject;
    }
}
