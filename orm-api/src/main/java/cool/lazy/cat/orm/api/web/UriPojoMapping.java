package cool.lazy.cat.orm.api.web;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.core.base.constant.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/6 11:10
 */
public class UriPojoMapping {

    private final static Map<String, Map<HttpMethod, EntryInfo>> POJO_MAPPING = new HashMap<>(Constant.DEFAULT_CONTAINER_SIZE);
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public void initMapping(ApiPojoManager apiPojoManager) {
        List<ApiPojoSubject> apiPojoSubjectList = apiPojoManager.getApiPojoSubjectList();
        List<UriInfoWrapper> sorted = new ArrayList<>(apiPojoSubjectList.size());
        for (ApiPojoSubject subject : apiPojoSubjectList) {
            List<EntryInfo> entryInfoList = subject.getEntryInfoList();
            for (EntryInfo entryInfo : entryInfoList) {
                String uri = subject.getNameSpace() + entryInfo.getPath();
                if (POJO_MAPPING.containsKey(uri)) {
                    POJO_MAPPING.get(uri).put(entryInfo.getMethod(), entryInfo);
                } else {
                    Map<HttpMethod, EntryInfo> map = new HashMap<>(entryInfoList.size());
                    map.put(entryInfo.getMethod(), entryInfo);
                    POJO_MAPPING.put(uri, map);
                }
                sorted.add(new UriInfoWrapper(subject.getPojoType(), entryInfo));
            }
        }
        if (logger.isDebugEnabled()) {
            sorted.sort(Comparator.comparingInt(u -> u.getPojoType().hashCode()));
            sorted.forEach(s -> logger.info(s.getPojoType().getName() + "-->" + s.getEntryInfo().getPath() + "-->" + s.getEntryInfo().getApi().getName()));
        }
    }

    public Map<HttpMethod,EntryInfo> getByUri(String uri) {
        return POJO_MAPPING.get(uri);
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
