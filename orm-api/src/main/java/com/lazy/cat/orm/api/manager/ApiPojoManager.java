package com.lazy.cat.orm.api.manager;

import com.lazy.cat.orm.api.exception.ExistNameSpaceException;
import com.lazy.cat.orm.api.exception.SamePathApiException;
import com.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import com.lazy.cat.orm.api.util.PathGenerator;
import com.lazy.cat.orm.api.web.annotation.ApiPojo;
import com.lazy.cat.orm.core.manager.Manager;
import com.lazy.cat.orm.core.manager.PojoManager;
import com.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import com.lazy.cat.orm.core.manager.subject.PojoSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/6 08:57
 */
public class ApiPojoManager implements Manager {

    protected List<ApiPojoSubject> apiPojoSubjectList;

    @Autowired
    public void initSubject(PojoManager pojoManager) {
        List<PojoSubject> subjectList = pojoManager.getPojoSubjectList().stream().filter(p -> p.getPojoType().getAnnotation(ApiPojo.class) != null).collect(Collectors.toList());
        apiPojoSubjectList = new ArrayList<>(subjectList.size());
        for (PojoSubject subject : subjectList) {
            ApiPojoSubject apiPojoSubject = new ApiPojoSubject();
            apiPojoSubject.setPojoType(subject.getPojoType());
            apiPojoSubject.setApiPojoAnnotation(subject.getPojoType().getAnnotation(ApiPojo.class));
            //初始化命名空间、api方法
            apiPojoSubject.initApiAnnotation();
            if (null != apiPojoSubject.getApiPojoAnnotation()) {
                boolean exist = this.apiPojoSubjectList.stream().anyMatch(p -> p.equals(apiPojoSubject));
                if (exist) {
                    throw new ExistNameSpaceException("已存在的nameSpace：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + apiPojoSubject.getNameSpace()+ "]");
                }
            }
            this.apiPojoSubjectList.add(apiPojoSubject);
        }
        this.checkRepeatedCombination();
    }

    private void checkRepeatedCombination() {
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            if (null == subject.getApiPojoAnnotation()) {
                continue;
            }
            String allCombinations = this.combination(subject.getNameSpace(), subject.getEntryMethodList());
            for (ApiPojoSubject contrast : apiPojoSubjectList) {
                if (null == contrast.getApiPojoAnnotation() || contrast == subject) {
                    continue;
                }
                List<ApiPojoSubject.EntryMethod> entryMethodList = contrast.getEntryMethodList();
                for (ApiPojoSubject.EntryMethod entryMethod : entryMethodList) {
                    if (allCombinations.contains(PathGenerator.path(contrast.getNameSpace(), entryMethod.getPath(), entryMethod.getMethod()))) {
                        throw new SamePathApiException("重复的api映射：代理类" + subject.getPojoType().getName() + "\t-->\t" + contrast.getPojoType().getName()
                                + "\tmappingApi：" + contrast.getNameSpace() + entryMethod.getPath() + "/" + entryMethod.getMethod());
                    }
                }
            }
        }
    }

    private String combination(String nameSpace, List<ApiPojoSubject.EntryMethod> entryMethodList) {
        StringBuilder sb = new StringBuilder();
        for (ApiPojoSubject.EntryMethod entryMethod : entryMethodList) {
            sb.append(PathGenerator.path(nameSpace, entryMethod.getPath(), entryMethod.getMethod())).append("|");
        }
        return sb.toString();
    }

    public List<ApiPojoSubject> getApiPojoSubjectList() {
        return apiPojoSubjectList;
    }

    public ApiPojoSubject getByPojoType(Class<?> pojoType) {
        List<ApiPojoSubject> apiPojoSubjectList = this.getApiPojoSubjectList();
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            if (subject.getPojoType() == pojoType) {
                return subject;
            }
        }
        throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
    }
}
