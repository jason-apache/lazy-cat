package cool.lazy.cat.orm.api.web.entrust.executor;

import cool.lazy.cat.orm.api.web.EntryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/6 17:47
 */
public interface ApiMethodExecutor {

    Object execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo);

}
