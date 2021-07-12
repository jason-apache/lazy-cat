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

    private String entrustPathNode = "api";

    public String getEntrustPathNode() {
        return entrustPathNode;
    }

    public ApiConfig setEntrustPathNode(String entrustPathNode) {
        if (null == entrustPathNode || StringUtil.isBlank(entrustPathNode)) {
            throw new IllegalArgumentException("entrustPathNode不能为空");
        }
        char prefix = entrustPathNode.charAt(0);
        char suffix = entrustPathNode.charAt(entrustPathNode.length() -1);
        if (entrustPathNode.contains("*") || prefix == ApiConstant.PATH_SYMBOL || prefix == '\\'
                || suffix == ApiConstant.PATH_SYMBOL || suffix == '\\') {
            throw new IllegalArgumentException("entrustPathNode首尾不能包含路径或通配符");
        }
        this.entrustPathNode = entrustPathNode;
        return this;
    }
}
