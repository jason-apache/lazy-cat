package com.lazy.cat.orm.api.web.entrust.controller;

import com.lazy.cat.orm.core.base.service.BaseService;
import com.lazy.cat.orm.core.context.FullAutoMappingContext;
import com.lazy.cat.orm.core.manager.BusinessManager;
import com.lazy.cat.orm.core.manager.subject.BusinessSubject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: mahao
 * @date: 2021/3/5 13:07
 */
public abstract class AbstractEntrustController implements EntrustApi {

    @Autowired
    protected BusinessManager businessManager;

    @Override
    public BusinessSubject getSubject() {
        return businessManager.getBusinessSubject(FullAutoMappingContext.getPojoType());
    }

    protected BaseService<?> getService() {
        return this.getSubject().getServiceSubject().getService();
    }
}
