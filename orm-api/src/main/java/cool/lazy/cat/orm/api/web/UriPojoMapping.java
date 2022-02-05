package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/6 11:10
 * 全局api uri映射
 */
public class UriPojoMapping {

    private Map<String, Map<HttpMethod, EntryInfo>> uriMapping = Collections.emptyMap();
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public void initMapping(ApiPojoManager apiPojoManager) {
        List<ApiPojoSubject> apiPojoSubjectList = apiPojoManager.getApiPojoSubjectList();
        uriMapping = new HashMap<>(apiPojoSubjectList.size());
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            List<EntryInfo> entryInfoList = subject.getEntryInfoList();
            for (EntryInfo entryInfo : entryInfoList) {
                // 记录api路径对应的api entry
                uriMapping.computeIfAbsent(entryInfo.getFullPath(), m -> new HashMap<>()).putAll(subject.getEntryInfoMap().get(entryInfo.getFullPath()));
            }
        }
        if (logger.isDebugEnabled()) {
            this.print(apiPojoSubjectList);
        }
    }

    protected void print(List<ApiPojoSubject> apiPojoSubjectList) {
        List<UriInfoWrapper> sorted = new ArrayList<>(apiPojoSubjectList.size());
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            for (EntryInfo entryInfo : subject.getEntryInfoList()) {
                sorted.add(new UriInfoWrapper(subject.getPojoType(), entryInfo));
            }
        }
        sorted.sort(Comparator.comparingInt(u -> u.getPojoType().hashCode()));
        sorted.forEach(s -> logger.info(s.getPojoType().getName() + " --> " + s.getEntryInfo().getFullPath() + ": "+ Arrays.toString(s.getEntryInfo().getMethods()) +" --> " + s.getEntryInfo().getApi().getName()));
    }

    public Map<HttpMethod, EntryInfo> getByUri(String uri) {
        return uriMapping.get(uri);
    }

    public Map<String, Map<HttpMethod, EntryInfo>> getUriMapping() {
        return new HashMap<>(this.uriMapping);
    }

    private static final class UriInfoWrapper {
        private final Class<?> pojoType;
        private final EntryInfo entryInfo;

        public UriInfoWrapper(Class<?> pojoType, EntryInfo entryInfo) {
            this.pojoType = pojoType;
            this.entryInfo = entryInfo;
        }

        public Class<?> getPojoType() {
            return pojoType;
        }

        public EntryInfo getEntryInfo() {
            return entryInfo;
        }
    }
}
