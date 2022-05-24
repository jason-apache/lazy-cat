package cool.lazy.cat.orm.api;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.api.manager.provider.ApiPojoSubjectProvider;
import cool.lazy.cat.orm.api.manager.provider.MetaAnnotationApiPojoSubjectProvider;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    /**
     * apiPojo注册器实例类型
     */
    private Class<? extends ApiPojoSubjectProvider> apiPojoSubjectRegistryInstance = MetaAnnotationApiPojoSubjectProvider.class;
    /**
     * api方法配置
     */
    private List<ApiEntryConfig> apiEntries = Collections.emptyList();

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

    public Class<? extends ApiPojoSubjectProvider> getApiPojoSubjectRegistryInstance() {
        return apiPojoSubjectRegistryInstance;
    }

    public void setApiPojoSubjectRegistryInstance(Class<? extends ApiPojoSubjectProvider> apiPojoSubjectRegistryInstance) {
        this.apiPojoSubjectRegistryInstance = apiPojoSubjectRegistryInstance;
    }

    public List<ApiEntryConfig> getApiEntries() {
        return apiEntries;
    }

    public void setApiEntries(List<ApiEntryConfig> apiEntries) {
        this.apiEntries = apiEntries;
    }

    public static class ApiEntryConfig {
        /**
         * pojo类型
         */
        private Class<?> pojoType;
        /**
         * 命名空间, 默认类名小驼峰
         */
        private String nameSpace;
        /**
         * api方法属性
         */
        private List<ApiEntryProperty> properties;
        /**
         * 字段查询条件
         */
        private List<QueryFilter> queryFilters = Collections.emptyList();
        /**
         * 附加参数
         */
        private Map<String, String> parameters = Collections.emptyMap();

        public Class<?> getPojoType() {
            return pojoType;
        }

        public void setPojoType(Class<?> pojoType) {
            this.pojoType = pojoType;
        }

        public String getNameSpace() {
            return nameSpace;
        }

        public void setNameSpace(String nameSpace) {
            this.nameSpace = nameSpace;
        }

        public List<ApiEntryProperty> getProperties() {
            return properties;
        }

        public void setProperties(List<ApiEntryProperty> properties) {
            this.properties = properties;
        }

        public List<QueryFilter> getQueryFilters() {
            return queryFilters;
        }

        public void setQueryFilters(List<QueryFilter> queryFilters) {
            this.queryFilters = queryFilters;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }
    }

    public static class ApiEntryProperty {
        /**
         * 访问路径, 完整路径为path+nameSpace
         */
        private String path;
        /**
         * api方法
         */
        private Class<? extends ApiMethodEntry> api;
        /**
         * 允许的http访问请求
         */
        private HttpMethod[] allowMethods = {HttpMethod.POST};
        /**
         * 附加参数
         */
        private Map<String, String> parameters = Collections.emptyMap();

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Class<? extends ApiMethodEntry> getApi() {
            return api;
        }

        public void setApi(Class<? extends ApiMethodEntry> api) {
            this.api = api;
        }

        public HttpMethod[] getAllowMethods() {
            return allowMethods;
        }

        public void setAllowMethods(HttpMethod[] allowMethods) {
            this.allowMethods = allowMethods;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }
    }

    public static class QueryFilter {
        /**
         * 字段
         */
        private String field;
        /**
         * 条件
         */
        private Class<? extends ConditionType> condition;
        /**
         * 附加参数
         */
        private Map<String, String> parameters = Collections.emptyMap();

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Class<? extends ConditionType> getCondition() {
            return condition;
        }

        public void setCondition(Class<? extends ConditionType> condition) {
            this.condition = condition;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }
    }
}
