package cool.lazy.cat.orm.core.jdbc.mapping;


import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
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

    public TriggerInfoImpl(cool.lazy.cat.orm.annotation.Trigger trigger) {
        if (ReflectUtil.canInstantiate(trigger.type())) {
            if (!Trigger.class.isAssignableFrom(trigger.type())) {
                throw new UnsupportedOperationException("不支持的类型: " + trigger.type() + ", 期望的类型: " + Trigger.class.getName());
            }
            this.trigger = Caster.cast(trigger.type());
        } else {
            this.trigger = Trigger.class;
        }
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
