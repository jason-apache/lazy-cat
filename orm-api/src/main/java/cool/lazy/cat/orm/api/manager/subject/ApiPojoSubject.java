package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.exception.ExistPathApiException;
import cool.lazy.cat.orm.api.util.PathGenerator;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.manager.subject.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/6 08:57
 */
public class ApiPojoSubject implements Subject {

    protected Class<?> pojoType;
    protected ApiPojo apiPojoAnnotation;
    protected String nameSpace;
    protected List<EntryInfo> entryInfoList = Collections.emptyList();

    public void initApiAnnotation() {
        if (null != apiPojoAnnotation) {
            entryInfoList = new ArrayList<>();
            this.nameSpace = PathGenerator.format(apiPojoAnnotation.nameSpace());
            if (StringUtil.isBlank(this.nameSpace)) {
                String name = pojoType.getSimpleName();
                // 转小驼峰
                this.nameSpace = PathGenerator.format(StringUtil.upper2Lower(name));
            }
            for (Entry entry : apiPojoAnnotation.entry()) {
                EntryInfo entryMethod = new EntryInfo(this, PathGenerator.format(entry.path()), entry.api(), entry.method());
                boolean exist = entryInfoList.stream().anyMatch(e -> e.equals(entryMethod));
                if (exist) {
                    throw new ExistPathApiException("已存在的path：代理类" + pojoType.getName() + "[value：" + entryMethod.getPath() + "，type：" + entry.method()  + "]");
                }
                entryInfoList.add(entryMethod);
            }
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

    public ApiPojoSubject setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public ApiPojo getApiPojoAnnotation() {
        return apiPojoAnnotation;
    }

    public ApiPojoSubject setApiPojoAnnotation(ApiPojo apiPojoAnnotation) {
        this.apiPojoAnnotation = apiPojoAnnotation;
        return this;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public ApiPojoSubject setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public List<EntryInfo> getEntryInfoList() {
        return entryInfoList;
    }

    public ApiPojoSubject setEntryInfoList(List<EntryInfo> entryInfoList) {
        this.entryInfoList = entryInfoList;
        return this;
    }
}
