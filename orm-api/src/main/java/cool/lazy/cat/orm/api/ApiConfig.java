package cool.lazy.cat.orm.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: mahao
 * @date: 2021/3/14 11:59
 */
@ConfigurationProperties(prefix = "cool.lazy-cat.servlet")
public class ApiConfig {

    /**
     * 是否开启api映射
     */
    private boolean enabled = true;
    /**
     * 是否开启映射通用api
     */
    private boolean enabledCommonApi = true;
    /**
     * api映射根路径前缀
     */
    private String apiPath = "lazy-cat";

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabledCommonApi() {
        return enabledCommonApi;
    }

    public void setEnabledCommonApi(boolean enabledCommonApi) {
        this.enabledCommonApi = enabledCommonApi;
    }
}
