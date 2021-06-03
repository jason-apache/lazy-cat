package com.lazy.cat.orm.api.web;

import com.lazy.cat.orm.api.manager.ApiPojoManager;
import com.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import com.lazy.cat.orm.core.base.constant.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/6 11:10
 */
public class UriPojoMapping {

    private final static Map<UriInfo, ApiPojoSubject> POJO_MAPPING = new HashMap<>(Constant.DEFAULT_CONTAINER_SIZE);
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public void initMapping(ApiPojoManager apiPojoManager) {
        List<ApiPojoSubject> apiPojoSubjectList = apiPojoManager.getApiPojoSubjectList();
        List<UriInfoWrapper> sorted = new ArrayList<>(apiPojoSubjectList.size());
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            List<ApiPojoSubject.EntryMethod> entryMethodList = subject.getEntryMethodList();
            for (ApiPojoSubject.EntryMethod entryMethod : entryMethodList) {
                UriInfo uriInfo = new UriInfo(subject.getNameSpace() + entryMethod.getPath(), entryMethod.getMethod().toString());
                POJO_MAPPING.put(uriInfo, subject);
                sorted.add(new UriInfoWrapper(subject.getPojoType(), uriInfo));
            }
        }
        if (logger.isDebugEnabled()) {
            sorted.sort(Comparator.comparingInt(u -> u.getPojoType().hashCode()));
            sorted.forEach(s -> logger.info(s.getPojoType().getName() + "-->" + s.getUriInfo()));
        }
    }

    public ApiPojoSubject getByUriAndType(String uri, String type) {
        return POJO_MAPPING.get(new UriInfo(uri, type));
    }

    private static final class UriInfoWrapper {
        private final Class<?> pojoType;
        private final UriInfo uriInfo;

        public UriInfoWrapper(Class<?> pojoType, UriInfo uriInfo) {
            this.pojoType = pojoType;
            this.uriInfo = uriInfo;
        }

        public Class<?> getPojoType() {
            return pojoType;
        }

        public UriInfo getUriInfo() {
            return uriInfo;
        }
    }

    public static final class UriInfo {

        private final String uri;
        private final String method;

        UriInfo(String uri, String method) {
            this.uri = uri;
            this.method = method;
        }

        public String getUri() {
            return uri;
        }

        public String getMethod() {
            return method;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UriInfo uriInfo = (UriInfo) o;
            if (!Objects.equals(uri, uriInfo.uri)) {
                return false;
            }
            return Objects.equals(method, uriInfo.method);
        }

        @Override
        public int hashCode() {
            int result = uri != null ? uri.hashCode() : 0;
            result = 31 * result + (method != null ? method.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return this.uri + "\t" + this.method;
        }
    }
}
