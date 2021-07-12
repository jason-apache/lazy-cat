package cool.lazy.cat.orm.api.web.entrust.provider;

import cool.lazy.cat.orm.api.web.EntryInfo;
import org.springframework.http.HttpMethod;

/**
 * @author: mahao
 * @date: 2021/7/7 15:29
 */
public interface ApiEntryInfoProvider {

    EntryInfo provider(String uri, HttpMethod method);
}
