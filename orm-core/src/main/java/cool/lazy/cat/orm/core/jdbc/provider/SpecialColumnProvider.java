package cool.lazy.cat.orm.core.jdbc.provider;

import cool.lazy.cat.orm.core.jdbc.component.SpecialColumn;

/**
 * @author: mahao
 * @date: 2021/11/3 11:05
 */
public interface SpecialColumnProvider {

    /**
     * 根据类型提供特殊列处理器
     * @param type 特殊列类型
     * @param <T> 泛型类型
     * @return 特殊列处理器
     */
    <T extends SpecialColumn> T provider(Class<T> type);
}
