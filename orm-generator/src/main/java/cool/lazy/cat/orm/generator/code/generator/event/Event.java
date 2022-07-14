package cool.lazy.cat.orm.generator.code.generator.event;

/**
 * @author : jason.ma
 * @date : 2022/7/13 16:43
 */
public interface Event {

    /**
     * @return 事件名称
     */
    EventName name();

    /**
     * @return 事件参数
     */
    EventParam param();

    /**
     * @return 事件状态
     */
    EventState state();

    /**
     * 改变状态
     * @param state 状态
     */
    void state(EventState state);
}
