package cool.lazy.cat.orm.core.jdbc.provider;


import cool.lazy.cat.orm.core.jdbc.component.id.IdGenerator;

/**
 * @author: mahao
 * @date: 2021/3/30 20:08
 * id提供者
 */
public interface IdGeneratorProvider {

    /**
     * 提供一个id生成器
     * @param type id生成器类型
     * @return id生成器
     */
    IdGenerator provider(Class<? extends IdGenerator> type);
}
