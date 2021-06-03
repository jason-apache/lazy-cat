package com.lazy.cat.orm.api.web.entrust.handle;


import com.lazy.cat.orm.api.web.entrust.ErrorData;

/**
 * @author: mahao
 * @date: 2021/4/24 21:05
 * 异常处理器
 */
public interface ExceptionHandler {

    /**
     * 返回处理器是否能够处理该异常
     * @param e 异常
     * @return 是否能够处理该异常
     */
    boolean canSolve(Throwable e);

    /**
     * 处理异常，返回响应数据
     * @param e 异常
     * @return 响应数据
     */
    ErrorData handle(Throwable e);

    /**
     * 定义处理器的执行顺序
     * @return 执行顺序
     */
    int order();
}
