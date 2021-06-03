package com.lazy.cat.orm.api.web.entrust.controller;

import com.lazy.cat.orm.api.web.entrust.handle.EntrustForwardHandle;
import com.lazy.cat.orm.api.web.entrust.handle.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/4 10:11
 * 对404的请求二次转发
 * 实际上实现api自动映射的根本在于：
 *        对访问一个本不存在的api进行转发，根据ApiPojo注解声明的entry路径
 *        转发至EntrustController中的预设Api，如果再次404，则代表Api不存在
 * @see EntrustController
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class UnKnowApiEntrustController extends BasicErrorController {

    @Autowired
    protected EntrustForwardHandle entrustForwardHandle;
    protected List<ExceptionHandler> exceptionHandlerList;

    @Autowired
    private void initExceptionHandler(List<ExceptionHandler> exceptionHandlerList) {
        exceptionHandlerList.sort(Comparator.comparingInt(ExceptionHandler::order));
        this.exceptionHandlerList = exceptionHandlerList;
    }

    public UnKnowApiEntrustController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status.value() == HttpStatus.NOT_FOUND.value()) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            RequestDispatcher dispatcher = entrustForwardHandle.handle(request, response);
            if (null == dispatcher) {
                return super.error(request);
            }
            try {
                // 委托转发
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                for (ExceptionHandler handler : exceptionHandlerList) {
                    if (handler.canSolve(e)) {
                        return new ResponseEntity<>(handler.handle(e).getData(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return null;
        }
        return super.error(request);
    }
}
