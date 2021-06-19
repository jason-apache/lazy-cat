package com.jason.test.service.impl;


import com.jason.test.pojo.User;
import com.jason.test.service.UserService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/4/24 16:55
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public Collection<User> batchInsert(Collection<User> pojoCollection, boolean cascade) {
        return super.batchInsert(pojoCollection, cascade);
    }
}
