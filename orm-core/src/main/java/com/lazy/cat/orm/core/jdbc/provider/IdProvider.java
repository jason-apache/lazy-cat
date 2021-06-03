package com.lazy.cat.orm.core.jdbc.provider;


import com.lazy.cat.orm.core.jdbc.component.id.IdGenerator;

/**
 * @author: mahao
 * @date: 2021/3/30 20:08
 * id提供者
 */
public interface IdProvider {

    /**
     * 提供一个id集合，根据id生成器类型创建id并返回
     * @param type id生成器类型
     * @param args id生成器所需参数
     * @return id集合
     */
    Object[] provider(Class<? extends IdGenerator> type, Object... args);
}
