package cool.lazy.cat.orm.generator.code.generator.event.listener;

import cool.lazy.cat.orm.generator.code.generator.event.Event;
import cool.lazy.cat.orm.generator.code.generator.event.EventName;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:05
 */
public interface EventListener {

    /**
     * @return 监听器排序
     */
    int order();
    /**
     * @return 监听指定事件
     */
    EventName listenFor();
    /**
     * @return 是否满足监听条件
     */
    boolean match(Event event);
    /**
     * 执行监听器内部逻辑
     */
    void execute(Event event);
}
