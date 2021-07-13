package cool.lazy.cat.orm.api.web.entrust;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/7/13 12:23
 */
public interface EntrustController {

    Object entrust(HttpServletRequest request, HttpServletResponse response);
}
