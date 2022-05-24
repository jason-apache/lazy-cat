package cool.lazy.cat.orm.api.manager;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.exception.ExistNameSpaceException;
import cool.lazy.cat.orm.api.exception.SamePathApiException;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.manager.provider.ApiPojoSubjectProvider;
import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void initSubject(ApiPojoSubjectProvider apiPojoSubjectProvider) {
        List<ApiPojoSubject> apiPojoSubjects = apiPojoSubjectProvider.provider();
        apiPojoSubjectMap = new HashMap<>(apiPojoSubjects.size());
        nameSpaceMap = new HashMap<>(apiPojoSubjects.size());
        for (ApiPojoSubject apiPojoSubject : apiPojoSubjects) {
            if (nameSpaceMap.containsKey(apiPojoSubject.getNameSpace())) {
                throw new ExistNameSpaceException("已存在的nameSpace：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + apiPojoSubject.getNameSpace()+ "]");
            }
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
