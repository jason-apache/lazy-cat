package cool.lazy.cat.orm.core.jdbc.mapping;

import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;

/**
 * @author: mahao
 * @date: 2021/10/18 11:41
 */
public interface TriggerInfo {

    Class<? extends Trigger> getTrigger();

    int getSort();
}
