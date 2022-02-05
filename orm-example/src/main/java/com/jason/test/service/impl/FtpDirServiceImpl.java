package com.jason.test.service.impl;

import com.jason.test.pojo.mysql.FtpDir;
import com.jason.test.service.FtpDirService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: mahao
 * @date: 2021/4/24 16:55
 */
@Service
public class FtpDirServiceImpl extends BaseServiceImpl<FtpDir> implements FtpDirService {

    @Override
    public FtpDir update(FtpDir pojo, boolean cascade, boolean ignoreNull) {
        return super.update(pojo, cascade, ignoreNull);
    }
}
