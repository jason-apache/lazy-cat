package com.lazy.cat.orm.core.jdbc.component.id;

import java.util.Collection;
import java.util.UUID;

/**
 * @author: mahao
 * @date: 2021/3/30 19:54
 * UUID生成器
 */
public class UUIdGenerator implements IdGenerator {

    @Override
    public Object[] generator(Object ...args) {
        Object instance = args[0];
        if (instance instanceof Collection) {
            Collection<?> instanceRef = (Collection<?>) instance;
            Object[] ids = new Object[instanceRef.size()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = UUID.randomUUID().toString().replaceAll("-", "");
            }
            return ids;
        }
        return new Object[]{UUID.randomUUID().toString().replaceAll("-", "")};
    }
}
