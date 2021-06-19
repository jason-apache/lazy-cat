package cool.lazy.cat.orm.api.web.entrust.handle;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/3/6 09:44
 * 处理api转发的处理器
 */
public interface EntrustForwardHandle {

    /**
     * 委托转发
     * @param request 请求体
     * @param response 响应体
     * @return 转发器，不无匹配项则返回空
     */
    RequestDispatcher handle(HttpServletRequest request, HttpServletResponse response);

}
