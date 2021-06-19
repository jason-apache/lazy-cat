package cool.lazy.cat.orm.api.web.entrust.controller;

import cool.lazy.cat.orm.api.web.annotation.FullyAutomaticArgument;
import cool.lazy.cat.orm.api.web.constant.ApiEntry;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.bo.WebResponse;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 09:15
 */
@RestController(value = "defaultEntrustController")
@RequestMapping("#{apiConfig.entrustPath}")
public class EntrustController extends AbstractEntrustController {

    @RequestMapping(value = ApiEntry.QUERY)
    public WebResponse query(@RequestBody QueryInfo queryInfo) {
        return WebResponse.success(getService().select(queryInfo));
    }

    @RequestMapping(value = ApiEntry.QUERY_PAGE)
    public WebResponse queryPage(@RequestBody QueryInfo queryInfo) {
        return WebResponse.success(getService().selectPage(queryInfo));
    }

    @RequestMapping(value = ApiEntry.SAVE)
    public WebResponse save(@FullyAutomaticArgument List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), false));
    }

    @RequestMapping(value = ApiEntry.SAVE_CASCADE)
    public WebResponse saveCascade(@FullyAutomaticArgument List<Object> dataList) {
        return WebResponse.success(getService().batchSave(Caster.cast(dataList), true));
    }

    @RequestMapping(value = ApiEntry.REMOVE)
    public WebResponse remove(@FullyAutomaticArgument List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), false);
        return WebResponse.success(null);
    }

    @RequestMapping(value = ApiEntry.REMOVE_CASCADE)
    public WebResponse removeCascade(@FullyAutomaticArgument List<Object> dataList) {
        getService().batchDeleteByInfer(Caster.cast(dataList), true);
        return WebResponse.success(null);
    }

    @RequestMapping(value = ApiEntry.REMOVE_BY_IDS)
    public WebResponse removeByIds(@RequestBody List<Object> ids) {
        getService().deleteByIdsAndInfer(FullAutoMappingContext.getPojoType(), Caster.cast(ids));
        return WebResponse.success(null);
    }
}