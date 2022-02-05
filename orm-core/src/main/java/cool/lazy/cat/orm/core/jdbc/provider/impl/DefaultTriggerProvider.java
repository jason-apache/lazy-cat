package cool.lazy.cat.orm.core.jdbc.provider.impl;


import cool.lazy.cat.orm.core.base.exception.InitFailedException;
import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;
import cool.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/4/14 12:54
 */
public class DefaultTriggerProvider implements TriggerProvider {

    protected final Map<Class<? extends Trigger>, Trigger> triggerMap = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private void initTriggerMap(List<Trigger> triggerList) {
        for (Trigger trigger : triggerList) {
            this.triggerMap.put(trigger.getClass(), trigger);
        }
    }

    @Override
    public Trigger provider(Class<? extends Trigger> triggerType) {
        Trigger trigger = this.triggerMap.get(triggerType);
        if (null == trigger) {
            try {
                trigger = triggerType.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new InitFailedException("初始化trigger失败！", e);
            }
            triggerMap.put(triggerType, trigger);
        }
        return trigger;
    }
}
