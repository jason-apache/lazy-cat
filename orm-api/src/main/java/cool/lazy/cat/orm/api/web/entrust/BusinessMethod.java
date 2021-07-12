package cool.lazy.cat.orm.api.web.entrust;

import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.bo.WebResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/6 18:32
 */
public interface BusinessMethod {

    WebResponse query(QueryInfo queryInfo, HttpServletRequest request, HttpServletResponse response, String name);

    WebResponse queryPage(QueryInfo queryInfo);

    WebResponse save(List<Object> dataList);

    WebResponse saveCascade(List<Object> dataList);

    WebResponse remove(List<Object> dataList);

    WebResponse removeCascade(List<Object> dataList);

    WebResponse removeByIds(List<Object> ids);
}