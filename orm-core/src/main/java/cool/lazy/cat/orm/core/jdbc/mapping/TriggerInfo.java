package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;

/**
 * @author: mahao
 * @date: 2021/4/14 12:02
 * 触发器信息
 */
public class TriggerInfo {

    /**
     * 触发器类型
     */
    private final Class<? extends Trigger> trigger;
    private final boolean onInsert;
    private final boolean onUpdate;
    private final int sort;

    public TriggerInfo(cool.lazy.cat.orm.core.base.annotation.Trigger trigger) {
        this.trigger = trigger.type();
        this.onInsert = trigger.onInsert();
        this.onUpdate = trigger.onUpdate();
        this.sort = trigger.sort();
    }

    public Class<? extends Trigger> getTrigger() {
        return trigger;
    }

    public boolean isOnInsert() {
        return onInsert;
    }

    public boolean isOnUpdate() {
        return onUpdate;
    }

    public int getSort() {
        return sort;
    }
}
