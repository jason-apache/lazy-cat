package com.jason.test.component.api;

import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.entrust.executor.intercepter.ApiMethodExecuteInterceptor;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: mahao
 * @date: 2022-02-05 15:49
 */
@Component
public class ApiInterceptor implements ApiMethodExecuteInterceptor {

    @Override
    public void beforeExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry) {
    }

    @Override
    public void pendingExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args) {
    }

    @Override
    public void afterExecute(HttpServletRequest request, HttpServletResponse response, EntryInfo entryInfo, ApiMethodEntry methodEntry, Object[] args, Object executeResult) {
    }
}
