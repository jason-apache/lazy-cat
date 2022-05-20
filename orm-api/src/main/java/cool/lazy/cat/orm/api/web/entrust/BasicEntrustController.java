package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.exception.UnKnowTargetBeanException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.FullAutoMappingContext;
import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.api.web.entrust.executor.ApiMethodExecutor;
import cool.lazy.cat.orm.api.web.entrust.provider.ApiEntryInfoProvider;
import cool.lazy.cat.orm.base.util.StringUtil;
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

    public BasicEntrustController(ApiEntryInfoProvider apiEntryInfoProvider, ApiMethodExecutor apiMethodExecutor, String apiPath) {
        super(apiEntryInfoProvider, apiMethodExecutor, apiPath);
        if (null == apiPath || StringUtil.isEmpty(apiPath)) {
            throw new IllegalArgumentException("apiPath不能为空");
        }
        char prefix = apiPath.charAt(0);
        char suffix = apiPath.charAt(apiPath.length() -1);
        if (apiPath.contains("*") || prefix == ApiConstant.PATH_SYMBOL || prefix == '\\'
                || suffix == ApiConstant.PATH_SYMBOL || suffix == '\\') {
            throw new IllegalArgumentException("apiPath首尾不能包含路径或通配符");
        }
        this.apiPathLength = (ApiConstant.PATH_SYMBOL + apiPath).length();
    }

    /**
     * 模糊匹配请求api
     * @param request 请求体
     * @param response 响应体
     * @return api处理结果
     */
    @Override
    @RequestMapping(value = "#{apiConfig.apiPath}/**")
    public Object entrust(HttpServletRequest request, HttpServletResponse response) {
        String uri = this.cut(urlPathHelper.getRequestUri(request));
        // 从全局api映射中匹配
        EntryInfo entryInfo = apiEntryInfoProvider.provider(uri, HttpMethod.resolve(request.getMethod()));
        if (null == entryInfo) {
            throw new UnKnowTargetBeanException("未知请求");
        }
        try {
            FullAutoMappingContext.setPojoType(entryInfo.getPojoType());
            return super.apiMethodExecutor.execute(request, response, entryInfo);
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
