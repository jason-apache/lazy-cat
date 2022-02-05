package cool.lazy.cat.orm.api.web.entrust;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/13 12:23
 * 代理api方法控制器
 */
public interface EntrustController {

    /**
     * 处理请求
     * @param request 请求体
     * @param response 响应体
     * @return 处理完业务逻辑后的返回数据 将直接交由mvc处理
     */
    Object entrust(HttpServletRequest request, HttpServletResponse response);
}
