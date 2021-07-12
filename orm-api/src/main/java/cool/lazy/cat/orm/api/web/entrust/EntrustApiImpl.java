package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.web.entrust.executor.annotation.JsonWriter;
import cool.lazy.cat.orm.api.web.entrust.executor.annotation.Param;
import cool.lazy.cat.orm.api.web.entrust.executor.annotation.RequestBodyJsonObj;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.bo.WebResponse;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import cool.lazy.cat.orm.core.manager.BusinessManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @JsonWriter
    public WebResponse query(@RequestBodyJsonObj QueryInfo queryInfo, HttpServletRequest request, HttpServletResponse response,
                             @Param(name = "myName", defaultValue = "111") String name) {
        return WebResponse.success(getService().select(queryInfo));
    }

    @Override
    @JsonWriter
    public WebResponse queryPage(@RequestBodyJsonObj QueryInfo queryInfo) {
        return WebResponse.success(getService().selectPage(queryInfo));
    }

    @Override
    @JsonWriter
    public WebResponse save(@RequestBodyJsonObj List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), false));
    }

    @Override
    @JsonWriter
    public WebResponse saveCascade(@RequestBodyJsonObj List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), true));
    }

    @Override
    @JsonWriter
    public WebResponse remove(@RequestBodyJsonObj List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), false);
        return WebResponse.success(null);
    }

    @Override
    @JsonWriter
    public WebResponse removeCascade(@RequestBodyJsonObj List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), true);
        return WebResponse.success(null);
    }

    @Override
    @JsonWriter
    public WebResponse removeByIds(@RequestBodyJsonObj List<Object> ids) {
        getService().deleteByIdsAndInfer(FullAutoMappingContext.getPojoType(), Caster.cast(ids));
        return WebResponse.success(null);
    }
}
