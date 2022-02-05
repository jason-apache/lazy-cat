package cool.lazy.cat.orm.core.base.service.impl;

import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.service.CommonService;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/5 09:57
 */
public abstract class AbstractBaseService<P> extends AbstractServiceSupport implements BaseService<P> {

    @Autowired
    protected CommonService commonService;
    @Autowired
    protected BaseRepository baseRepository;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Class<?> getPojoType() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) this.getClass().getGenericSuperclass();
            Object clazz = p.getActualTypeArguments()[0];
            if (clazz instanceof Class) {
                return (Class<?>) p.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    @Override
    public SearchParam<P> buildSearchParam() {
        return commonService.buildSearchParam(Caster.cast(this.getPojoType()));
    }

    @Override
    public List<P> select(SearchParam<P> searchParam) {
        return this.getSelectTransactionTemplate(searchParam.getPojoType()).readOnly((s) -> commonService.select(searchParam));
    }

    @Override
    public PageResult<P> selectPage(SearchParam<P> searchParam) {
        return this.getSelectTransactionTemplate(searchParam.getPojoType()).readOnly((s) -> commonService.selectPage(searchParam));
    }

    @Override
    public P selectById(Object id) {
        Class<P> pojoType = Caster.cast(this.getPojoType());
        return this.getSelectTransactionTemplate(pojoType).readOnly((s) -> commonService.selectById(pojoType, id));
    }

    @Override
    public List<P> selectByIds(Collection<Object> ids) {
        Class<P> pojoType = Caster.cast(this.getPojoType());
        return this.getSelectTransactionTemplate(pojoType).readOnly((s) -> commonService.selectByIds(pojoType, ids));
    }
}
