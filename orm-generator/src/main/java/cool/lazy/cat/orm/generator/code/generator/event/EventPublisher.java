package cool.lazy.cat.orm.generator.code.generator.event;

import cool.lazy.cat.orm.generator.code.generator.event.listener.EventListener;
import cool.lazy.cat.orm.generator.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:04
 */
public class EventPublisher {

    private static final Map<EventName, List<EventListener>> LISTENER_MAP = new HashMap<>();
    private static final Set<EventName> SORTED_EVENT = new HashSet<>();

    public static void registry(EventListener listener) {
        LISTENER_MAP.computeIfAbsent(listener.listenFor(), k -> new ArrayList<>()).add(listener);
    }

    public static void publish(EventName name, EventParam param) {
        List<EventListener> eventListeners = LISTENER_MAP.get(name);
        if (CollectionUtil.isNotEmpty(eventListeners)) {
            if (!SORTED_EVENT.contains(name)) {
                // 排序
                eventListeners.sort(Comparator.comparingInt(EventListener::order));
                SORTED_EVENT.add(name);
            }
            Event event = new EventImpl(name, param);
            for (EventListener eventListener : eventListeners) {
                if (eventListener.match(event)) {
                    eventListener.execute(event);
                }
            }
        }
    }

    protected static class EventImpl implements Event {

        private final EventName name;
        private final EventParam param;
        private EventState state = EventState.EventStateEnum.READY;

        public EventImpl(EventName name, EventParam param) {
            this.name = name;
            this.param = param;
        }

        @Override
        public EventName name() {
            return name;
        }

        @Override
        public EventParam param() {
            return param;
        }

        @Override
        public EventState state() {
            return state;
        }

        @Override
        public void state(EventState state) {
            this.state = state;
        }
    }
}
