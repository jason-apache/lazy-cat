package com.jason.test.service.impl;


import com.jason.test.pojo.mysql.User;
import com.jason.test.service.UserService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: mahao
 * @date: 2021/4/24 16:55
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
}
