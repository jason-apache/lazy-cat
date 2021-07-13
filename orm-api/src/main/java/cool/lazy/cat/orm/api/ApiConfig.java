package cool.lazy.cat.orm.api;

import cool.lazy.cat.orm.api.web.constant.ApiConstant;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: mahao
 * @date: 2021/3/14 11:59
 */
@ConfigurationProperties(prefix = "cool.lazy-cat.servlet")
public class ApiConfig {

    private String apiPath = "lazy-cat";

    public String getApiPath() {
        return apiPath;
    }

    public ApiConfig setApiPath(String apiPath) {
        if (null == apiPath || StringUtil.isEmpty(apiPath)) {
            throw new IllegalArgumentException("apiPath不能为空");
        }
        char prefix = apiPath.charAt(0);
        char suffix = apiPath.charAt(apiPath.length() -1);
        if (apiPath.contains("*") || prefix == ApiConstant.PATH_SYMBOL || prefix == '\\'
                || suffix == ApiConstant.PATH_SYMBOL || suffix == '\\') {
            throw new IllegalArgumentException("apiPath首尾不能包含路径或通配符");
        }
        this.apiPath = apiPath;
        return this;
    }
}
