package cool.lazy.cat.orm.core.jdbc.component.trigger;

import cool.lazy.cat.orm.core.jdbc.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

/**
 * @author: mahao
 * @date: 2021/4/14 11:52
 * 触发器
 */
public interface Trigger extends CommonComponent {

    /**
     * 执行触发器
     * @param type sql操作类型
     * @param instance 操作数据
     */
    void execute(Class<? extends SqlType> type, Object instance);
}
