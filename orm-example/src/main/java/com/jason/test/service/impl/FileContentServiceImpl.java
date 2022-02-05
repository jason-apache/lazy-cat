package com.jason.test.service.impl;

import com.jason.test.pojo.mysql.FileContent;
import com.jason.test.service.FileContentService;
import cool.lazy.cat.orm.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: mahao
 * @date: 2021/7/13 13:23
 */
@Service
public class FileContentServiceImpl extends BaseServiceImpl<FileContent> implements FileContentService {

    @Override
    public FileContent update(FileContent pojo, boolean cascade, boolean ignoreNull) {
        return super.update(pojo, cascade, ignoreNull);
    }
}
