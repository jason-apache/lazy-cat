package com.lazy.cat.orm.core.jdbc.component.id;

/**
 * @author: mahao
 * @date: 2021/3/30 19:47
 */
public interface IdGenerator {

    /**
     * 生成id
     * @param args 数据集
     * @return id集
     */
    Object[] generator(Object... args);
}
