package com.lazy.cat.orm.api.web.entrust.handle;

import com.lazy.cat.orm.api.web.constant.ApiConstant;
import com.lazy.cat.orm.api.web.provider.ApiPathProvider;
import com.lazy.cat.orm.core.base.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2021/3/6 09:51
 */
public class DefaultEntrustForwardHandle implements EntrustForwardHandle {

    @Autowired
    protected ApiPathProvider apiPathProvider;
    @Value("#{apiConfig.entrustPath}")
    protected String entrustEntry;
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public RequestDispatcher handle(HttpServletRequest request, HttpServletResponse response) {
        String apiPath = apiPathProvider.getApiPath(request);
        if (StringUtil.isBlank(apiPath)) {
            return null;
        }
        RequestDispatcher requestDispatcher = null;
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            requestDispatcher = request.getServletContext().getRequestDispatcher(ApiConstant.PATH_SYMBOL + entrustEntry + apiPath);
        } catch (Exception e) {
            logger.warn("无法转发请求：" + apiPath + "，请检查entrustController是否存在此方法");
        }
        return requestDispatcher;
    }
}
