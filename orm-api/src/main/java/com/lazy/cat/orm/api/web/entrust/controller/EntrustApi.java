package com.lazy.cat.orm.api.web.entrust.controller;


import com.lazy.cat.orm.core.manager.subject.BusinessSubject;

/**
 * @author: mahao
 * @date: 2021/3/5 13:07
 * 委托处理api
 */
public interface EntrustApi {

    /**
     * 获取当前操作的pojo业务主体
     * @return pojo业务主体
     */
    BusinessSubject getSubject();
}
