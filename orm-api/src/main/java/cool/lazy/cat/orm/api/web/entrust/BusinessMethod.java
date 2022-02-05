package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.api.web.bo.QueryInfo;
import cool.lazy.cat.orm.api.web.bo.WebResponse;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/6 18:32
 * 内置api方法
 */
public interface BusinessMethod {

    WebResponse query(QueryInfo queryInfo);

    WebResponse queryPage(QueryInfo queryInfo);

    WebResponse save(List<Object> dataList);

    WebResponse saveCascade(List<Object> dataList);

    WebResponse remove(List<Object> dataList);

    WebResponse removeCascade(List<Object> dataList);

    WebResponse removeByIds(List<Object> ids);
}
