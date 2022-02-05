package cool.lazy.cat.orm.core.jdbc.component.id;

import cool.lazy.cat.orm.core.jdbc.component.CommonComponent;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/30 19:47
 */
public interface IdGenerator extends CommonComponent {

    /**
     * 生成id
     * @param instances 参数
     * @return id集
     */
    List<Object> generator(List<Object> instances);
}
