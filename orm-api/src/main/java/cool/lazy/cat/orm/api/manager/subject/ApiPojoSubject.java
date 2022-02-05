package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.exception.ExistPathApiException;
import cool.lazy.cat.orm.api.util.PathGenerator;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.EntryInfoImpl;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;
import cool.lazy.cat.orm.core.manager.subject.Subject;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/6 08:57
 */
public class ApiPojoSubject extends AbstractParameterizationInfo implements Subject {

    protected final Class<?> pojoType;
    protected String nameSpace;
    protected Map<String, Map<HttpMethod, EntryInfo>> entryInfoMap = Collections.emptyMap();
    protected List<EntryInfo> entryInfoList = Collections.emptyList();
    protected Map<String, ApiQueryFilterInfo> queryFilterInfoMap = Collections.emptyMap();

    public ApiPojoSubject(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    public void init(ApiPojo apiPojoAnnotation) {
        if (null != apiPojoAnnotation) {
            entryInfoMap = new HashMap<>(apiPojoAnnotation.entry().length);
            entryInfoList = new ArrayList<>(apiPojoAnnotation.entry().length);
            // 初始化命名空间
            this.nameSpace = PathGenerator.format(apiPojoAnnotation.nameSpace());
            if (StringUtil.isBlank(this.nameSpace)) {
                String name = pojoType.getSimpleName();
                // 转小驼峰
                this.nameSpace = PathGenerator.format(StringUtil.upper2Lower(name));
            }
            // 分析api
            for (Entry entry : apiPojoAnnotation.entry()) {
                if (entry.api() == ApiMethodEntry.class) {
                    throw new IllegalArgumentException("不是一个ApiMethodEntry实现类：" + entry.api().getName());
                }
                if (CollectionUtil.isEmpty(entry.methods())) {
                    throw new IllegalArgumentException("method为空：" + entry.path());
                }
                // 生成完整路径
                String fullPath = nameSpace + PathGenerator.format(entry.path());
                EntryInfo entryMethod = new EntryInfoImpl(pojoType, nameSpace, fullPath, entry.api(), entry.methods(), entry.parameters());
                for (HttpMethod httpMethod : entryMethod.getMethods()) {
                    if (entryInfoMap.get(entryMethod.getFullPath()) != null && entryInfoMap.get(entryMethod.getFullPath()).containsKey(httpMethod)) {
                        throw new ExistPathApiException("已存在的path：代理类" + pojoType.getName() + "[value：" + entryMethod.getFullPath() + "，type：" + Arrays.toString(entry.methods()) + "]");
                    }
                    // 生成method映射结构
                    entryInfoMap.computeIfAbsent(entryMethod.getFullPath(), l -> new HashMap<>()).put(httpMethod, entryMethod);
                }
                entryInfoList.add(entryMethod);
            }
            super.initParameter(apiPojoAnnotation.parameters());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiPojoSubject that = (ApiPojoSubject) o;
        return Objects.equals(nameSpace, that.nameSpace);
    }

    @Override
    public int hashCode() {
        return nameSpace != null ? nameSpace.hashCode() : 0;
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public Map<String, Map<HttpMethod, EntryInfo>> getEntryInfoMap() {
        return entryInfoMap;
    }

    public List<EntryInfo> getEntryInfoList() {
        return entryInfoList;
    }

    public Map<String, ApiQueryFilterInfo> getQueryFilterInfoMap() {
        return queryFilterInfoMap;
    }

    public void setQueryFilterInfoMap(Map<String, ApiQueryFilterInfo> queryFilterInfoMap) {
        this.queryFilterInfoMap = queryFilterInfoMap;
    }
}
