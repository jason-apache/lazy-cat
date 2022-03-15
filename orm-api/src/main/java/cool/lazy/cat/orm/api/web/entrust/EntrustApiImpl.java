package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.service.CommonApiService;
import cool.lazy.cat.orm.api.web.FullAutoMappingContext;
import cool.lazy.cat.orm.api.web.bo.QueryInfo;
import cool.lazy.cat.orm.api.web.bo.WebResponse;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.manager.ServiceManager;

import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/6 18:06
 */
public class EntrustApiImpl extends AbstractEntrustApi implements EntrustApi, BusinessMethod {

    protected final CommonApiService commonApiService;

    public EntrustApiImpl(ServiceManager serviceManager, CommonApiService commonApiService) {
        super(serviceManager);
        this.commonApiService = commonApiService;
    }

    @Override
    public WebResponse query(QueryInfo queryInfo) {
        return WebResponse.success(commonApiService.select(queryInfo));
    }

    @Override
    public WebResponse queryPage(QueryInfo queryInfo) {
        return WebResponse.success(commonApiService.selectPage(queryInfo));
    }

    @Override
    @SuppressWarnings("all")
    public WebResponse save(List<Object> dataList) {
        getService().save((Collection) Caster.cast(dataList), false);
        return WebResponse.success(dataList);
    }

    @Override
    @SuppressWarnings("all")
    public WebResponse saveCascade(List<Object> dataList) {
        getService().save((Collection) Caster.cast(dataList), true);
        return WebResponse.success(dataList);
    }

    @Override
    @SuppressWarnings("all")
    public WebResponse remove(List<Object> dataList) {
        getService().deleteByInfer((Collection) Caster.cast(dataList), false);
        return WebResponse.success(null);
    }

    @Override
    @SuppressWarnings("all")
    public WebResponse removeCascade(List<Object> dataList) {
        getService().deleteByInfer((Collection) Caster.cast(dataList), true);
        return WebResponse.success(null);
    }

    @Override
    public WebResponse removeByIds(List<Object> ids) {
        getService().deleteByIdsAndInfer(FullAutoMappingContext.getPojoType(), Caster.cast(ids));
        return WebResponse.success(null);
    }
}
