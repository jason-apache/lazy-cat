package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;
import cool.lazy.cat.orm.core.manager.subject.Subject;

import java.util.Collections;
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

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public Map<String, Map<HttpMethod, EntryInfo>> getEntryInfoMap() {
        return entryInfoMap;
    }

    public void setEntryInfoMap(Map<String, Map<HttpMethod, EntryInfo>> entryInfoMap) {
        this.entryInfoMap = entryInfoMap;
    }

    public List<EntryInfo> getEntryInfoList() {
        return entryInfoList;
    }

    public void setEntryInfoList(List<EntryInfo> entryInfoList) {
        this.entryInfoList = entryInfoList;
    }

    public Map<String, ApiQueryFilterInfo> getQueryFilterInfoMap() {
        return queryFilterInfoMap;
    }

    public void setQueryFilterInfoMap(Map<String, ApiQueryFilterInfo> queryFilterInfoMap) {
        this.queryFilterInfoMap = queryFilterInfoMap;
    }

    public void setParameterMapping(Map<String, String> parameterMapping) {
        super.parameterMapping = parameterMapping;
    }
}
