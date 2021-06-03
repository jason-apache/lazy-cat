package com.jason.test.service.impl;

import com.jason.test.pojo.UserDir;
import com.jason.test.service.UserDirService;
import com.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: mahao
 * @date: 2021/4/24 16:55
 */
@Service
public class UserDirServiceImpl extends BaseServiceImpl<UserDir> implements UserDirService {

    @Override
    public UserDir insert(UserDir pojo, boolean cascade) {
        return super.insert(pojo, cascade);
    }

    @Override
    public UserDir update(UserDir pojo, boolean cascade, boolean ignoreNull) {
        return super.update(pojo, cascade, ignoreNull);
    }
}
