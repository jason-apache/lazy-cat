package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;

/**
 * @author: mahao
 * @date: 2021/4/14 12:02
 * 触发器信息
 */
public class TriggerInfoImpl implements TriggerInfo {

    /**
     * 触发器类型
     */
    private final Class<? extends Trigger> trigger;
    private final int sort;

    public TriggerInfoImpl(cool.lazy.cat.orm.core.base.annotation.Trigger trigger) {
        this.trigger = trigger.type();
        this.sort = trigger.sort();
    }

    @Override
    public Class<? extends Trigger> getTrigger() {
        return trigger;
    }

    @Override
    public int getSort() {
        return sort;
    }
}
