package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.exception.ExistPathApiException;
import cool.lazy.cat.orm.api.util.PathGenerator;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.manager.subject.Subject;
import org.springframework.web.bind.annotation.RequestMethod;

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
    protected List<EntryMethod> entryMethodList = Collections.emptyList();

    public void initApiAnnotation() {
        if (null != apiPojoAnnotation) {
            entryMethodList = new ArrayList<>();
            this.nameSpace = PathGenerator.format(apiPojoAnnotation.nameSpace());
            if (StringUtil.isBlank(this.nameSpace)) {
                String name = pojoType.getSimpleName();
                // 转小驼峰
                this.nameSpace = PathGenerator.format(StringUtil.upper2Lower(name));
            }
            for (Entry entry : apiPojoAnnotation.entry()) {
                EntryMethod entryMethod = new EntryMethod(PathGenerator.format(entry.path()), PathGenerator.format(entry.mappingApi()), entry.method());
                boolean exist = entryMethodList.stream().anyMatch(e -> e.equals(entryMethod));
                if (exist) {
                    throw new ExistPathApiException("已存在的path：代理类" + pojoType.getName() + "[value：" + entryMethod.getPath() + "，type：" + entry.method()  + "]");
                }
                entryMethodList.add(entryMethod);
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

    public List<EntryMethod> getEntryMethodList() {
        return entryMethodList;
    }

    public ApiPojoSubject setEntryMethodList(List<EntryMethod> entryMethodList) {
        this.entryMethodList = entryMethodList;
        return this;
    }

    public static final class EntryMethod {

        private final String path;
        private final String mappingApi;
        private final RequestMethod method;

        EntryMethod(String path, String mappingApi, RequestMethod method) {
            this.path = path;
            this.mappingApi = mappingApi;
            this.method = method;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            EntryMethod that = (EntryMethod) o;
            if (!Objects.equals(path, that.path)) {
                return false;
            }
            return method == that.method;
        }

        @Override
        public int hashCode() {
            int result = path != null ? path.hashCode() : 0;
            result = 31 * result + (method != null ? method.hashCode() : 0);
            return result;
        }

        public String getPath() {
            return path;
        }

        public String getMappingApi() {
            return mappingApi;
        }

        public RequestMethod getMethod() {
            return method;
        }
    }
}
