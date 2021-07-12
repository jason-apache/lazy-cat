package cool.lazy.cat.orm.api.web.entrust.executor.holder;

import cool.lazy.cat.orm.api.web.EntryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/7 15:58
 */
public interface ExecutorHolder {

    void execute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo);
}
