package cool.lazy.cat.orm.api.web.entrust.provider;

import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.EntryInfo;

/**
 * @author: mahao
 * @date: 2021/7/7 15:29
 * api入口提供者
 */
public interface ApiEntryInfoProvider {

    /**
     * 根据uri和method提供对应的api
     * @param uri uri
     * @param method method
     * @return api入口
     */
    EntryInfo provider(String uri, HttpMethod method);
}
