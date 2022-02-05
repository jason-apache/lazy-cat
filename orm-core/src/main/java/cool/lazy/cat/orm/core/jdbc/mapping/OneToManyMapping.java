package cool.lazy.cat.orm.core.jdbc.mapping;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2022-02-01 16:57
 */
public interface OneToManyMapping extends PojoMapping {

    /**
     * @return 一对多存放多的一方的容器
     */
    Class<? extends Collection<?>> getContainerType();
}
