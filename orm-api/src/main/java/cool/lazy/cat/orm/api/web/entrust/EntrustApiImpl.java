package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.bo.WebResponse;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import cool.lazy.cat.orm.core.manager.BusinessManager;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/6 18:06
 */
public class EntrustApiImpl extends AbstractEntrustApi implements EntrustApi, BusinessMethod {

    public EntrustApiImpl(BusinessManager businessManager) {
        super(businessManager);
    }

    @Override
    public WebResponse query(QueryInfo queryInfo) {
        return WebResponse.success(getService().select(queryInfo));
    }

    @Override
    public WebResponse queryPage(QueryInfo queryInfo) {
        return WebResponse.success(getService().selectPage(queryInfo));
    }

    @Override
    public WebResponse save(List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), false));
    }

    @Override
    public WebResponse saveCascade(List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), true));
    }

    @Override
    public WebResponse remove(List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), false);
        return WebResponse.success(null);
    }

    @Override
    public WebResponse removeCascade(List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), true);
        return WebResponse.success(null);
    }

    @Override
    public WebResponse removeByIds(List<Object> ids) {
        getService().deleteByIdsAndInfer(FullAutoMappingContext.getPojoType(), Caster.cast(ids));
        return WebResponse.success(null);
    }
}
