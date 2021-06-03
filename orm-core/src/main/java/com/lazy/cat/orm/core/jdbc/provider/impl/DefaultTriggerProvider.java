package com.lazy.cat.orm.core.jdbc.provider.impl;


import com.lazy.cat.orm.core.base.exception.InitFailedException;
import com.lazy.cat.orm.core.jdbc.component.trigger.Trigger;
import com.lazy.cat.orm.core.jdbc.provider.TriggerProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/4/14 12:54
 */
public class DefaultTriggerProvider implements TriggerProvider {

    protected Map<Class<? extends Trigger>, Trigger> triggerMap = new HashMap<>();

    @Autowired(required = false)
    private void initTriggerMap(List<Trigger> triggerList) {
        this.triggerMap = triggerList.stream().collect(Collectors.toMap(Trigger::getClass, Function.identity()));
    }

    @Override
    public Trigger provider(Class<? extends Trigger> triggerType) {
        Trigger trigger = this.triggerMap.get(triggerType);
        if (null == trigger) {
            try {
                trigger = triggerType.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new InitFailedException("初始化trigger失败！");
            }
            triggerMap.put(triggerType, trigger);
        }
        return trigger;
    }
}
