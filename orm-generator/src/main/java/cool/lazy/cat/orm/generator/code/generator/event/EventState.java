package cool.lazy.cat.orm.generator.code.generator.event;

/**
 * @author : jason.ma
 * @date : 2022/7/13 17:20
 */
public interface EventState {

    enum EventStateEnum implements EventState {
        READY, PROCESSED, DISCARD
    }
}
