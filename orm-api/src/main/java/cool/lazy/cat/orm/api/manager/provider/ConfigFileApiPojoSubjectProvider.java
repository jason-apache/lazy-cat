package cool.lazy.cat.orm.api.manager.provider;

import cool.lazy.cat.orm.api.ApiConfig;
import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.exception.ExistNameSpaceException;
import cool.lazy.cat.orm.api.exception.ExistPathApiException;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.manager.subject.ApiQueryFilterInfo;
import cool.lazy.cat.orm.api.util.PathGenerator;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.EntryInfoImpl;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2022-05-23 23:26
 * 根据配置文件的参数生成api映射
 */
public class ConfigFileApiPojoSubjectProvider implements ApiPojoSubjectProvider {

    protected final List<ApiConfig.ApiEntryConfig> apiEntries;

    public ConfigFileApiPojoSubjectProvider(List<ApiConfig.ApiEntryConfig> apiEntries) {
        this.apiEntries = apiEntries;
    }

    @Override
    public List<ApiPojoSubject> provider() {
        List<ApiPojoSubject> result = new ArrayList<>(apiEntries.size());
        Set<String> nameSpaces = new HashSet<>(apiEntries.size());
        for (ApiConfig.ApiEntryConfig apiEntry : apiEntries) {
            String nameSpace = apiEntry.getNameSpace();
            Class<?> pojoType = apiEntry.getPojoType();
            ApiPojoSubject apiPojoSubject = new ApiPojoSubject(pojoType);
            apiPojoSubject.setNameSpace(nameSpace);
            //初始化命名空间、api方法
            this.initApiPojoSubject(apiPojoSubject, apiEntry);
            if (nameSpaces.contains(apiPojoSubject.getNameSpace())) {
                throw new ExistNameSpaceException("已存在的nameSpace：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + apiPojoSubject.getNameSpace()+ "]");
            }
            // 初始化字段查询条件
            this.initQueryFilter(apiPojoSubject, apiEntry);
            nameSpaces.add(apiPojoSubject.getNameSpace());
            result.add(apiPojoSubject);
        }
        return result;
    }

    protected void initApiPojoSubject(ApiPojoSubject apiPojoSubject, ApiConfig.ApiEntryConfig entryConfig) {
        // 初始化命名空间
        String nameSpace = PathGenerator.format(apiPojoSubject.getNameSpace());
        if (StringUtil.isBlank(nameSpace)) {
            String name = apiPojoSubject.getPojoType().getSimpleName();
            // 转小驼峰
            nameSpace = PathGenerator.format(StringUtil.upper2Lower(name));
        }
        Map<String, Map<HttpMethod, EntryInfo>> entryInfoMap = new HashMap<>(entryConfig.getProperties().size());
        List<EntryInfo> entryInfoList = new ArrayList<>(entryConfig.getProperties().size());
        for (ApiConfig.ApiEntryProperty property : entryConfig.getProperties()) {
            Class<? extends ApiMethodEntry> api = property.getApi();
            if (api.isInterface()) {
                throw new IllegalArgumentException("不是一个ApiMethodEntry实现类：" + api.getName());
            }
            if (!ApiMethodEntry.class.isAssignableFrom(api)) {
                throw new UnsupportedOperationException("不支持类型, 请自定义实现: " + api);
            }
            if (CollectionUtil.isEmpty(property.getAllowMethods())) {
                throw new IllegalArgumentException("method为空：" + property.getPath());
            }
            // 生成完整路径
            String fullPath = nameSpace + PathGenerator.format(property.getPath());
            EntryInfoImpl entryMethod = new EntryInfoImpl(apiPojoSubject.getPojoType(), nameSpace, fullPath, Caster.cast(api), property.getAllowMethods());
            entryMethod.setParameterMapping(property.getParameters());
            for (HttpMethod httpMethod : entryMethod.getMethods()) {
                if (entryInfoMap.get(entryMethod.getFullPath()) != null && entryInfoMap.get(entryMethod.getFullPath()).containsKey(httpMethod)) {
                    throw new ExistPathApiException("已存在的path：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + entryMethod.getFullPath() + "，type：" + Arrays.toString(property.getAllowMethods()) + "]");
                }
                // 生成method映射结构
                entryInfoMap.computeIfAbsent(entryMethod.getFullPath(), l -> new HashMap<>()).put(httpMethod, entryMethod);
            }
            entryInfoList.add(entryMethod);
        }
        apiPojoSubject.setEntryInfoList(entryInfoList);
        apiPojoSubject.setNameSpace(nameSpace);
        apiPojoSubject.setEntryInfoMap(entryInfoMap);
        apiPojoSubject.setParameterMapping(entryConfig.getParameters());
    }

    protected void initQueryFilter(ApiPojoSubject apiPojoSubject, ApiConfig.ApiEntryConfig entryConfig) {
        List<ApiConfig.QueryFilter> queryFilters = entryConfig.getQueryFilters();
        Map<String, ApiQueryFilterInfo> apiQueryFilterInfoMap = new HashMap<>(queryFilters.size());
        for (ApiConfig.QueryFilter queryFilter : queryFilters) {
            ApiQueryFilterInfo apiQueryFilterInfo = new ApiQueryFilterInfo(queryFilter.getCondition());
            apiQueryFilterInfo.setParameterMapping(queryFilter.getParameters());
            apiQueryFilterInfoMap.put(queryFilter.getField(), apiQueryFilterInfo);
        }
        if (!apiQueryFilterInfoMap.isEmpty()) {
            apiPojoSubject.setQueryFilterInfoMap(apiQueryFilterInfoMap);
        }
    }
}
