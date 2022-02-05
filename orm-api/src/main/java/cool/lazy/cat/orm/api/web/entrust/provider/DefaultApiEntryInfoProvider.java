package cool.lazy.cat.orm.api.web.entrust.provider;

import cool.lazy.cat.orm.api.exception.BadRequestException;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.UriPojoMapping;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/7 15:39
 */
public class DefaultApiEntryInfoProvider implements ApiEntryInfoProvider {

    protected final UriPojoMapping uriPojoMapping;

    public DefaultApiEntryInfoProvider(UriPojoMapping uriPojoMapping) {
        this.uriPojoMapping = uriPojoMapping;
    }

    @Override
    public EntryInfo provider(String uri, HttpMethod method) {
        // 从全局api映射中匹配
        Map<HttpMethod, EntryInfo> mappingByUri = uriPojoMapping.getByUri(uri);
        if (null == mappingByUri) {
            return null;
        }
        EntryInfo entryInfo = mappingByUri.get(method);
        if (null == entryInfo) {
            throw new BadRequestException("不支持的请求方法: " + method.name());
        }
        return entryInfo;
    }
}
