package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.exception.UnKnowTargetBeanException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.executor.holder.ExecutorHolder;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/12 17:00
 */
@RestController
public class BasicEntrustController extends AbstractEntrustController implements EntrustController {

    protected final int apiPathLength;

    public BasicEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ExecutorHolder executorHolder, String apiPath) {
        super(apiEntryInfoProvider, executorHolder, apiPath);
        this.apiPathLength = (ApiConstant.PATH_SYMBOL + apiPath).length();
    }

    @Override
    @RequestMapping(value = "#{apiConfig.apiPath}/**")
    public Object entrust(HttpServletRequest request, HttpServletResponse response) {
        String uri = this.cut(urlPathHelper.getRequestUri(request));
        EntryInfo entryInfo = apiEntryInfoProvider.provider(uri, HttpMethod.resolve(request.getMethod()));
        if (null == entryInfo) {
            throw new UnKnowTargetBeanException("未知请求");
        }
        try {
            FullAutoMappingContext.setPojoType(entryInfo.getApiPojoSubject().getPojoType());
            return executorHolder.execute(request, response, entryInfo);
        } finally {
            FullAutoMappingContext.removePojoType();
        }
    }

    private String cut(String uri) {
        if (uri.length() > apiPathLength) {
            return uri.substring(apiPathLength);
        }
        return uri;
    }
}
