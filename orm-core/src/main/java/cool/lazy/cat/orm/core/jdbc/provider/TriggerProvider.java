package cool.lazy.cat.orm.core.jdbc.provider;


import cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger;

/**
 * @author: mahao
 * @date: 2021/4/14 12:53
 * 触发器提供者
 */
public interface TriggerProvider {

    /**
     * 根据触发器类型提供一个触发器的实例
     * @param triggerType 触发器类型
     * @return 触发器
     */
    Trigger provider(Class<? extends Trigger> triggerType);
}
