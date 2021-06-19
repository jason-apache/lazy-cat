package cool.lazy.cat.orm.core.manager.subject;


import cool.lazy.cat.orm.core.base.repository.BaseRepository;

/**
 * @author: mahao
 * @date: 2021/3/7 17:05
 */
public class RepositorySubject implements Subject {

    protected Class<?> pojoType;
    protected PojoSubject pojoSubject;
    /**
     * 记录上下文中对应的repository
     */
    protected BaseRepository<?> repository;

    public Class<?> getPojoType() {
        return pojoType;
    }

    public RepositorySubject setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public PojoSubject getPojoSubject() {
        return pojoSubject;
    }

    public RepositorySubject setPojoSubject(PojoSubject pojoSubject) {
        this.pojoSubject = pojoSubject;
        return this;
    }

    public BaseRepository<?> getRepository() {
        return repository;
    }

    public RepositorySubject setRepository(BaseRepository<?> repository) {
        this.repository = repository;
        return this;
    }
}
