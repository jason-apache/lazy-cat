package cool.lazy.cat.orm.api.manager;

import cool.lazy.cat.orm.api.exception.ExistNameSpaceException;
import cool.lazy.cat.orm.api.exception.SamePathApiException;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.manager.subject.ApiQueryFilterInfo;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.ApiQueryFilter;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.manager.PojoManager;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/6 08:57
 */
public class ApiPojoManagerImpl implements ApiPojoManager {

    protected Map<Class<?>, ApiPojoSubject> apiPojoSubjectMap;
    protected Map<String, ApiPojoSubject> nameSpaceMap;

    @Autowired
    public void initSubject(PojoManager pojoManager, PojoTableManager pojoTableManager) {
        List<PojoSubject> subjectList = pojoManager.getPojoSubjectList().stream().filter(p -> p.getPojoType().getAnnotation(ApiPojo.class) != null).collect(Collectors.toList());
        apiPojoSubjectMap = new HashMap<>(subjectList.size());
        nameSpaceMap = new HashMap<>(subjectList.size());
        for (PojoSubject subject : subjectList) {
            ApiPojoSubject apiPojoSubject = new ApiPojoSubject(subject.getPojoType());
            //初始化命名空间、api方法
            apiPojoSubject.init(subject.getPojoType().getAnnotation(ApiPojo.class));
            if (nameSpaceMap.containsKey(apiPojoSubject.getNameSpace())) {
                throw new ExistNameSpaceException("已存在的nameSpace：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + apiPojoSubject.getNameSpace()+ "]");
            }
            // 初始化字段查询条件
            this.initQueryFilter(apiPojoSubject, pojoTableManager.getByPojoType(subject.getPojoType()).getTableInfo().getFieldInfoMap());
            apiPojoSubjectMap.put(apiPojoSubject.getPojoType(), apiPojoSubject);
            nameSpaceMap.put(apiPojoSubject.getNameSpace(), apiPojoSubject);
        }
        this.checkRepeatedCombination();
    }

    /**
     * 检查pojo api路径是否冲突
     */
    private void checkRepeatedCombination() {
        // 以请求路径分组
        Map<String, List<EntryInfo>> allApiFullPathGroup = apiPojoSubjectMap.values().stream().flatMap(s -> s.getEntryInfoList().stream())
                .collect(Collectors.groupingBy(EntryInfo::getFullPath, Collectors.toList()));
        for (Map.Entry<String, List<EntryInfo>> entry : allApiFullPathGroup.entrySet()) {
            // 请求路径下只有一个api 则跳过
            if (entry.getValue().size() == 1) {
                continue;
            }
            ApiPojoSubject tempSubject = null;
            Set<HttpMethod> methods = new HashSet<>(entry.getValue().size());
            for (EntryInfo entryInfo : entry.getValue()) {
                ApiPojoSubject subject = nameSpaceMap.get(entryInfo.getNameSpace());
                if (null == tempSubject) {
                    tempSubject = subject;
                } else if (tempSubject != subject) {
                    // entryInfo的nameSpace不相等则代表不属于同一个pojo
                    throw new SamePathApiException("重复的api映射：代理类" + subject.getPojoType().getName() + "\t-->\t" + entryInfo.getFullPath() + Arrays.toString(entryInfo.getMethods()));
                }
                for (HttpMethod method : entryInfo.getMethods()) {
                    if (methods.contains(method)) {
                        throw new SamePathApiException("重复的api映射：代理类" + subject.getPojoType().getName() + "\t-->\t" + entryInfo.getFullPath() + Arrays.toString(entryInfo.getMethods()));
                    } else {
                        // 收集所有method
                        methods.add(method);
                    }
                }
            }
        }
    }

    /**
     * 初始字段化查询条件
     * @param apiPojoSubject apiPojo主体
     * @param fieldInfoMap pojo字段映射
     */
    protected void initQueryFilter(ApiPojoSubject apiPojoSubject, Map<String, PojoField> fieldInfoMap) {
        Map<String, ApiQueryFilterInfo> apiQueryFilterInfoMap = new HashMap<>(fieldInfoMap.size());
        for (Map.Entry<String, PojoField> entry : fieldInfoMap.entrySet()) {
            String key = entry.getKey();
            ApiQueryFilter apiQueryFilter = entry.getValue().getGetter().getAnnotation(ApiQueryFilter.class);
            if (null == apiQueryFilter) {
                continue;
            }
            apiQueryFilterInfoMap.put(key, new ApiQueryFilterInfo(apiQueryFilter));
        }
        if (!apiQueryFilterInfoMap.isEmpty()) {
            apiPojoSubject.setQueryFilterInfoMap(apiQueryFilterInfoMap);
        }
    }

    @Override
    public List<ApiPojoSubject> getApiPojoSubjectList() {
        return Collections.unmodifiableList(new ArrayList<>(apiPojoSubjectMap.values()));
    }

    @Override
    public ApiPojoSubject getByPojoType(Class<?> pojoType) {
        ApiPojoSubject subject = apiPojoSubjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的apiPojo类型：" + pojoType.getName());
        }
        return subject;
    }

    @Override
    public ApiPojoSubject getByNameSpace(String nameSpace) {
        ApiPojoSubject subject = nameSpaceMap.get(nameSpace);
        if (null == subject) {
            throw new UnKnowPojoException("未知的nameSpace：" + nameSpace);
        }
        return subject;
    }
}
